package manager;

import exception.ManagerSaveException;
import tasks.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private static List<String> taskCsv = writeCsvToArray();
    private static File file;
    private static final String TASK_FIELDS = "ID;TYPE;NAME;STATUS;DESCRIPTION;EPIC";

    public FileBackedTasksManager (String filePath) {
        super();
        this.file = new File(filePath);
    }

    public static void setFile(File file) {
        FileBackedTasksManager.file = file;
    }

    private void save() {
        try (PrintWriter printWriter = new PrintWriter(file, "Windows-1251")) {
            printWriter.write(TASK_FIELDS + "\n");
            for (Task task : getAllTasks()) {
                printWriter.write(task.toString().replace(",", ";") + "\n");
            }
            for (Task epic : getAllEpics()) {
                String[] str = epic.toString().split(",");
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < 5; i++) {
                    stringBuilder.append(str[i]);
                    stringBuilder.append(";");
                }
                printWriter.write(stringBuilder + "\n");
            }
            for (Task subtask : getAllSubtasks()) {
                printWriter.write(subtask.toString().replace(",", ";") + "\n");
            }
            printWriter.write("HISTORY" + "\n");
            for (Task historyTask : history()) {
                String[] str = historyTask.toString().split(",");
                printWriter.write(str[0] + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Обнаружена ошибка!");
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager.taskCsv = FileBackedTasksManager.writeCsvToArray();
        FileBackedTasksManager.setFile(file);
        FileBackedTasksManager fileBack = new FileBackedTasksManager(file.getPath());
        taskCsv.remove(0);
        taskCsv.remove("HISTORY");
        for (String line : taskCsv) {
            String[] str = line.split(";");
            if (str.length >= 5) {
                if (str[1].equals("TASK")) {
                    fileBack.addTask(fromString(Integer.parseInt(str[0])));
                } else if (str[1].equals("EPIC")) {
                    fileBack.addEpic((Epic) fromString(Integer.parseInt(str[0])));
                } else {
                    fileBack.addSubtask((Subtask) fromString(Integer.parseInt(str[0])));
                }
            }
        }
        for (String line : taskCsv) {
            String[] str = line.split(";");
            if (str.length == 1) {
                fileBack.getTask(Integer.parseInt(str[0]));
                fileBack.getEpic(Integer.parseInt(str[0]));
                fileBack.getSubtask(Integer.parseInt(str[0]));
            }
        }
        return fileBack;
    }

    private static List<String> writeCsvToArray() {
        if (Files.exists(Path.of(String.valueOf(file)))) {
            List<String> brList = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(file,
                    Charset.forName("Windows-1251")))) {
                while (br.ready()) {
                    brList.add(br.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return brList;
        }
        return null;
    }

    public static Task fromString(int id) {
        for (String line : taskCsv) {
            String[] str = line.split(";");
            if (!str[0].equals("ID")) {
                if (!str[0].equals("HISTORY")) {
                    int parseId = Integer.parseInt(str[0]);
                    if (parseId == id) {
                        if ((str[1]).equals("TASK")) {
                            Task task = new Task(str[2], str[4], Status.valueOf(str[3]));
                            task.setTaskId(id);
                            return task;
                        } else if ((str[1]).equals("SUBTASK")) {
                            int epicId = Integer.parseInt(str[5].substring(9));
                            Subtask subtask = new Subtask(str[2], str[4], Status.valueOf(str[3]), epicId);
                            subtask.setTaskId(id);
                            return subtask;
                        } else if ((str[1]).equals("EPIC")) {
                            Epic epic = new Epic(str[2], str[4]);
                            epic.setTaskId(id);
                            return epic;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public Task getTask(int taskId) {
        super.getTask(taskId);
        save();
        return super.getTask(taskId);
    }

    @Override
    public Epic getEpic(int epicId) {
        super.getEpic(epicId);
        save();
        return super.getEpic(epicId);
    }

    @Override
    public Subtask getSubtask(int sabtaskId) {
        super.getSubtask(sabtaskId);
        save();
        return super.getSubtask(sabtaskId);
    }

    @Override
    public void updateTask(Task taskNew) {
        super.updateTask(taskNew);
        save();
    }

    @Override
    public void deleteTask(int taskId) {
        super.deleteTask(taskId);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void updateEpic(Epic epicNew) {
        super.updateEpic(epicNew);
        save();
    }

    @Override
    public void deleteEpic(int epicId) {
        super.deleteEpic(epicId);
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void updateSubtask(Subtask subtaskNew) {
        super.updateSubtask(subtaskNew);
        save();
    }

    @Override
    public void deleteSubtask(int subtaskId) {
        super.deleteSubtask(subtaskId);
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    public static void main(String[] args) {

        FileBackedTasksManager fileBacked = new FileBackedTasksManager("file.csv");
        Task task = new Task("обычная задача", "Обычное описание", Status.NEW);
        Task task1 = new Task("Обычная задача2", "Обычное описание2", Status.NEW);

        Epic epic = new Epic("Эпик1", "ЭпикОписание1");
        Epic epic1 = new Epic("Эпик2", "ЭпикОписание2");

        Subtask subtask = new Subtask("Сабтаск1", "СабтаскОписание1", Status.NEW, 3);
        Subtask subtask2 = new Subtask("Сабтаск2", "СабтаскОписание2", Status.NEW, 5);

        fileBacked.addTask(task);
        fileBacked.addTask(task1);
        fileBacked.addEpic(epic);
        fileBacked.addSubtask(subtask);
        fileBacked.addSubtask(subtask2);

        fileBacked.getTask(1);
        fileBacked.getEpic(3);
        fileBacked.getEpic(5);

        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(new File
                ("file.csv"));

        System.out.println(fileBacked.writeCsvToArray().equals(fileBackedTasksManager.writeCsvToArray()));
    }
}
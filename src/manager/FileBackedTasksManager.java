package manager;

import exception.ManagerSaveException;
import tasks.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private static List<String> taskCsv = writeFileToList();
    private static File file;
    private static final String TASK_FIELDS = "ID;TYPE;NAME;STATUS;DESCRIPTION;EPIC;StartTime;Duration;EndTime";

    public FileBackedTasksManager (String filePath) {
        super();
        this.file = new File(filePath);
    }

    public FileBackedTasksManager () {}

    public static void setFile(File file) {
        FileBackedTasksManager.file = file;
    }

    public void save() {
        try (PrintWriter printWriter = new PrintWriter(file, "Windows-1251")) {
            printWriter.write(TASK_FIELDS + "\n");
            for (Task task : getAllTasks()) {
                printWriter.write(task.toString().replace(",", ";") + "\n");
            }
            for (Epic epic : getAllEpics()) {
                printWriter.write(epic.toString().replace(",", ";") + "\n");
            }
            for (Subtask subtask : getAllSubtasks()) {
                printWriter.write(subtask.toString().replace(",", ";") + "\n");
            }
            printWriter.write("HISTORY" + "\n");
            for (Task inHistoryTask : history()) {
                String[] split = inHistoryTask.toString().split(",");
                printWriter.write(split[0] + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Обнаружена ошибка!");
        }
    }



    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager.taskCsv = FileBackedTasksManager.writeFileToList();
        FileBackedTasksManager.setFile(file);
        FileBackedTasksManager fb = new FileBackedTasksManager(file.getPath());
        taskCsv.remove(0);
        taskCsv.remove("HISTORY");
        for (String line : taskCsv) {
            String[] str = line.split(";");
            if (str.length > 6) {
                if (str[1].equals("EPIC")) {
                    fb.addEpic((Epic) fromString(Integer.parseInt(str[0])));
                } else if (str[1].equals("TASK")) {
                    fb.addTask(fromString(Integer.parseInt(str[0])));
                } else {
                    fb.addSubtask((Subtask) fromString(Integer.parseInt(str[0])));
                }
            }
        }
        for (String lineStr : taskCsv) {
            String[] str = lineStr.split(";");
            if (str.length == 1) {
                fb.getTask(Integer.parseInt(str[0]));
                fb.getSubtask(Integer.parseInt(str[0]));
                fb.getEpic(Integer.parseInt(str[0]));

            }
        }
        return fb;
    }

    private static List<String> writeFileToList() {
        if (Files.exists(Path.of(String.valueOf(file)))) {
            List<String> brList = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(file,
                    Charset.forName("Windows-1251")))) {
                while (br.ready()) {
                    brList.add(br.readLine());
                }
            } catch (IOException exc) {
                exc.printStackTrace();
            }
            return brList;
        }
        return null;
    }

    public static Task fromString(int id) {
        for (String lineStr : taskCsv) {
            String[] str = lineStr.split(";");
            if (!str[0].equals("ID")) {
                if (!str[0].equals("HISTORY")) {
                    int parseId = Integer.parseInt(str[0]);
                    if (parseId == id) {
                        if ((str[1]).equals("TASK")) {
                            Task task = new Task(str[2], str[4], Status.valueOf(str[3]));
                            task.setTaskId(id);
                            if (!str[5].equals("null")) {
                                task.setStartTime(LocalDateTime.parse(str[5]));
                                task.setDuration(Duration.parse(str[6]));
                            }
                            return task;
                        } else if ((str[1]).equals("SUBTASK")) {
                            int epicId = Integer.parseInt(str[5].substring(9));
                            Subtask subtask = new Subtask(str[2], str[4], Status.valueOf(str[3]), epicId);
                            subtask.setTaskId(id);
                            if (!str[6].equals("null")) {
                                subtask.setStartTime(LocalDateTime.parse(str[6]));
                                subtask.setDuration(Duration.parse(str[7]));
                            }
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
    public int getIdGen() {
        return super.getIdGen();
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

        FileBackedTasksManager fb = new FileBackedTasksManager("files/file.csv");
        Task task = new Task("обычная задача", "Обычное описание", Status.NEW, LocalDateTime.of(2022,12,01,12,00), Duration.ofHours(12));
        Task task1 = new Task("Обычная задача2", "Обычное описание2", Status.NEW, LocalDateTime.of(2022,12,01,12,00), Duration.ofHours(10));

        Epic epic = new Epic("Эпик 1", "Описание 2");
        Epic epic1 = new Epic("Эпик 2", "Описание 2");
        Subtask subtask = new Subtask("Подзадача 1", "Описание 1", Status.DONE, 3,
                LocalDateTime.now(), Duration.ofDays(4));
        System.out.println(epic);
        fb.addTask(task);
        fb.addTask(task1);
        fb.addEpic(epic);
        fb.addEpic(epic1);
        fb.addSubtask(subtask);
        System.out.println(fb.getPrioritizedTasks());


        fb.getTask(1);
        fb.getEpic(3);
        fb.getEpic(4);
        FileBackedTasksManager fbff = FileBackedTasksManager.loadFromFile(new File
                ("files/file.csv"));
        System.out.println(fb.getAllEpics());
        System.out.println(fbff.getAllEpics());
        System.out.println(fb.getAllTasks());
        System.out.println(fbff.getAllTasks());
        System.out.println(fb.getAllSubtasks());
        System.out.println(fbff.getAllSubtasks());


    }

}
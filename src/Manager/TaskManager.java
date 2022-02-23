package Manager;

import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;

import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subTasks;

    public TaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void addSubTask(SubTask subtask) {
        if (epics.containsKey(subtask.getEpicNumber())) {
            subTasks.put(subtask.getId(), subtask);
            addSubTaskToEpic(epics.get(subtask.getEpicNumber()), subtask.getId());
            updateEpicStatus(epics.get(subtask.getEpicNumber()));
        } else {
            System.out.println("Подзадача НЕ добавлена. " +
                    "Связанный с подзадачей Эпик не найден.");
        }

    }

    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void printAll() {
        printAllTasks();
        printAllEpics();
        printAllSubTasks();
    }

    public void printAllTasks() {
        System.out.println("Все задачи типа Task");
        if (!tasks.isEmpty()) {
            for (Task taskSout : tasks.values()) {
                System.out.println(taskSout);
            }
        } else {
            System.out.println("Задач типа Task не обнаружено");
        }
    }

    public void printAllEpics() {
        if (!epics.isEmpty()) {
            System.out.println("Все задачи типа Epic");
            for (Epic epicSout : epics.values()) {
                System.out.println(epicSout);
            }
        } else {
            System.out.println("Задач типа Epic не обнаружено");
        }
    }

    public void printAllSubTasks() {
        System.out.println("Все задачи типа SubTask");
        if (!subTasks.isEmpty()) {
            for (SubTask subSout : subTasks.values()) {
                System.out.println(subSout);
            }
        } else {
            System.out.println("Задач типа SubTask не обнаружено");
        }
    }

    public void printAllSubTasksByEpic(int taskId) {
        if (epics.containsKey(taskId) && epics.get(taskId).getSubTasks().size() > 0) {
            System.out.println("Список SubTasks для Эпика" + epics.get(taskId));
            for (int idSout : epics.get(taskId).getSubTasks()) {
                System.out.println(subTasks.get(idSout));
            }
        } else {
            System.out.println("Эпик с данным id отсутствует");
        }

    }

    public void deleteAllTasks() {
        tasks.clear();
        subTasks.clear();
        epics.clear();
    }

    public void getTaskById(int taskId) {
        if (tasks.containsKey(taskId)) {
            System.out.println("Задача с идентификатором " + taskId + " " + tasks.get(taskId));
        } else if (subTasks.containsKey(taskId)) {
            System.out.println("Задача с идентификатором " + taskId + " " + subTasks.get(taskId));
        } else if (epics.containsKey(taskId)) {
            System.out.println("Задача с идентификатором " + taskId + " " + epics.get(taskId));
        } else {
            System.out.println("Задача с данным иденитификатором не обнаружена");
        }

    }

    public void removeTaskbyId(int taskId) {
        if (tasks.containsKey(taskId)) {
            tasks.remove(taskId);
        } else if (subTasks.containsKey(taskId)) {
            subTasks.remove(taskId);
        } else if (epics.containsKey(taskId)) {  //доработать удаление Эпиков и сабов
            epics.remove(taskId);
        } else {
            System.out.println("Задача с данным иденитификатором не обнаружена");
        }
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
    }

    //public void updateEpic()
}

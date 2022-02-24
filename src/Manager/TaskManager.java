package Manager;

import Tasks.Epic;
import Tasks.Status;
import Tasks.SubTask;
import Tasks.Task;

import java.util.ArrayList;
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
            addTaskToEpic(epics.get(subtask.getEpicNumber()), subtask.getId());
        } else {
            System.out.println("Подзадача НЕ добавлена. " + "Связанный с подзадачей Эпик не найден.");
        }

    }

    public void addTaskToEpic(Epic epic, int idTask) {
        ArrayList<Integer> id = epic.getSubTasks();
        id.add(idTask);
        epic.setSubTasks(id);
        updateEpic(epic);
    }

    public void updateEpic(Epic epic) {
        int statusNEW = 0;
        int statusDONE = 0;
        for (Integer subId : epic.getSubTasks()) {
            if (subTasks.get(subId).getStatus().equals(Status.NEW)) {
                statusNEW++;
            } else if (subTasks.get(subId).getStatus().equals(Status.DONE)) {
                statusDONE++;
            }
        }
        if (epic.getSubTasks().isEmpty()) {
            epic.setStatus(Status.NEW);
        } else if (epic.getSubTasks().size() == statusNEW) {
            epic.setStatus(Status.NEW);
        } else if (epic.getSubTasks().size() == statusDONE) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
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

        if (!tasks.isEmpty()) {
            System.out.println("Все задачи типа Task");
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

        if (!subTasks.isEmpty()) {
            System.out.println("Все задачи типа SubTask");
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
            updateEpic(epics.get(taskId));
        } else if (epics.containsKey(taskId)) {
            if (epics.get(taskId).getSubTasks().size() > 0) {
                for (int subId : epics.get(taskId).getSubTasks()) {
                    subTasks.remove(subId);
                }
            }
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
        updateEpic(epics.get(subTask.getEpicNumber()));
    }


}

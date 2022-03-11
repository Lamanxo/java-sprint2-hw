package manager;

import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager{
    private HashMap<Long, Task> tasks;
    private HashMap<Long, Epic> epics;
    private HashMap<Long, SubTask> subTasks;
    private long IdGen = 0;
    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
    }
    @Override
    public void addTask(Task task) {

        tasks.put(task.getId(), task);
    }

    public long getNewId() {
        long idNew = ++IdGen;
        IdGen = idNew;
        return idNew;
    }
    @Override
    public void addSubTask(SubTask subtask) {
        if (epics.containsKey(subtask.getEpicNumber())) {
            subTasks.put(subtask.getId(), subtask);
            addTaskToEpic(epics.get(subtask.getEpicNumber()), subtask.getId());
        } else {
            System.out.println("Подзадача НЕ добавлена. " + "Связанный с подзадачей Эпик не найден.");
        }

    }
    @Override
    public void addTaskToEpic(Epic epic, long idTask) {
        ArrayList<Long> id = epic.getSubTasks();
        id.add(idTask);
        epic.setSubTasks(id);
        updateEpic(epic);
    }
    @Override
    public void updateEpic(Epic epic) {
        int statusNEW = 0;
        int statusDONE = 0;
        for (Long subId : epic.getSubTasks()) {
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
    @Override
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
    @Override
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
    @Override
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
    @Override
    public void printAllSubTasksByEpic(long taskId) {
        if (epics.containsKey(taskId) && epics.get(taskId).getSubTasks().size() > 0) {
            System.out.println("Список SubTasks для Эпика" + epics.get(taskId));
            for (long idSout : epics.get(taskId).getSubTasks()) {
                System.out.println(subTasks.get(idSout));
            }
        } else {
            System.out.println("Эпик с данным id отсутствует");
        }

    }
    @Override
    public void deleteAllTasks() {
        tasks.clear();
        subTasks.clear();
        epics.clear();
    }

    public void getTaskById(long taskId) {
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
    @Override
    public void removeTaskById(long taskId) {
        if (tasks.containsKey(taskId)) {
            tasks.remove(taskId);
        } else if (subTasks.containsKey(taskId)) {
            subTasks.remove(taskId);
            updateEpic(epics.get(taskId));
        } else if (epics.containsKey(taskId)) {
            if (epics.get(taskId).getSubTasks().size() > 0) {
                for (long subId : epics.get(taskId).getSubTasks()) {
                    subTasks.remove(subId);
                }
            }
            epics.remove(taskId);
        } else {
            System.out.println("Задача с данным иденитификатором не обнаружена");
        }
    }
    @Override
    public void updateTask(Task task) {

        tasks.put(task.getId(), task);
    }
    @Override
    public void updateSubTask(SubTask subTask) {

        subTasks.put(subTask.getId(), subTask);
        updateEpic(epics.get(subTask.getEpicNumber()));
    }


}

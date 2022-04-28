package manager;

import tasks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int idGen = 0;

    @Override
    public void setIdGen() {
        idGen++;
    }

    @Override
    public void addTask(Task task) {
        if (task == null)
            return;
        setIdGen();
        task.setTaskId(idGen);
        tasks.put(idGen, task);
    }

    @Override
    public Task getTask(int taskId) {
        if (tasks.containsKey(taskId))
            inMemoryHistoryManager.add(tasks.get(taskId));
        return tasks.get(taskId);
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void updateTask(int taskId, Task taskNew) {
        taskNew.setTaskId(taskId);
        tasks.put(taskId, taskNew);
    }

    @Override
    public void deleteTask(int taskId) {
        if (tasks.containsKey(taskId)) {
            tasks.remove(taskId);
            inMemoryHistoryManager.remove(taskId);
        }
    }

    @Override
    public void deleteAllTasks() {
        for (int taskId : tasks.keySet()) {
            inMemoryHistoryManager.remove(taskId);
        }
        tasks.clear();
    }

    @Override
    public void addEpic(Epic epic) {
        if (epic == null)
            return;
        setIdGen();
        epic.setTaskId(idGen);
        epics.put(idGen, epic);
    }

    @Override
    public Epic getEpic(int epicId) {
        if (epics.containsKey(epicId))
            inMemoryHistoryManager.add(epics.get(epicId));
        return epics.get(epicId);
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void updateEpic(int epicId, Epic epicNew) {
        if (!epics.containsKey(epicId) && epicNew == null)
            return;
        Epic epic = epics.get(epicId);
        epicNew.setTaskId(epicId);
        epicNew.setSubTaskslist(epic.getSubTaskslist());
        epicNew.setStatus();
        epics.put(epicId, epicNew);
    }

    @Override
    public void deleteEpic(int epicId) {
        if (!epics.containsKey(epicId))
            return;
        Epic epic = epics.get(epicId);
        ArrayList<Integer> keyList = new ArrayList<>();
        for (Subtask subtask : epic.getSubTaskslist()) {
            for (int key : subtasks.keySet()) {
                if (subtask.equals(subtasks.get(key))) {
                    keyList.add(key);
                }
            }
        }
        for (int key : keyList) {
            subtasks.remove(key);
            inMemoryHistoryManager.remove(key);
        }
        inMemoryHistoryManager.remove(epicId);
        epics.remove(epicId);
    }

    @Override
    public void deleteAllEpics() {
        for (int epicId : epics.keySet()) {
            for (int subId : subtasks.keySet()) {
                inMemoryHistoryManager.remove(subId);
            }
            inMemoryHistoryManager.remove(epicId);
        }
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (subtask == null)
            return;
        if (!epics.containsKey(subtask.getEpicId()))
            return;
        putSubtaskInEpic(subtask);
        setIdGen();
        subtask.setTaskId(idGen);
        subtasks.put(idGen, subtask);
    }

    private void putSubtaskInEpic(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        epic.addSubtaskInList(subtask);
        epic.setStatus();
    }

    @Override
    public Subtask getSubtask(int sabtaskId) {
        if (subtasks.containsKey(sabtaskId))
            inMemoryHistoryManager.add(subtasks.get(sabtaskId));
        return subtasks.get(sabtaskId);
    }

    @Override
    public void updateSubtask(int subtaskId, Subtask subtasNew) {
        if (subtasks.containsKey(subtaskId) && subtasNew == null)
            return;
        Subtask subtask = subtasks.get(subtaskId);
        if (subtask != null) {
            subtasNew.setTaskId(subtaskId);
            Epic epic = epics.get(subtask.getEpicId());
            int index = epic.getSubTaskslist().indexOf(subtask);
            epic.getSubTaskslist().set(index, subtasNew);
            subtasks.put(subtaskId, subtasNew);
            epic.setStatus();
        }
    }

    @Override
    public List<Subtask> getSubtasksByEpicId(int epicId) {
        if (epics.containsKey(epicId)) {
            Epic epic = epics.get(epicId);
            return epic.getSubTaskslist();
        } else {
            return null;
        }
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteSubtask(int subtaskId) {
        if (subtasks.containsKey(subtaskId)) {
            Subtask subtask = subtasks.get(subtaskId);
            Epic epic = epics.get(subtask.getEpicId());
            epic.getSubTaskslist().remove(subtask);
            subtasks.remove(subtaskId);
            inMemoryHistoryManager.remove(subtaskId);
            epic.setStatus();
        }
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubTaskslist().clear();
            epic.setStatus();
        }
        for (int subId : subtasks.keySet()) {
            inMemoryHistoryManager.remove(subId);
        }
        subtasks.clear();
    }

    @Override
    public List<Task> history() {
        return inMemoryHistoryManager.getHistory();
    }
}

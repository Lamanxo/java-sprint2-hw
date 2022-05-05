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
    private int idGen;

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
    public void updateTask(Task taskNew) {
        if (tasks.containsKey(taskNew.getTaskId())) {
            tasks.put(taskNew.getTaskId(), taskNew);
        }
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
    public void updateEpic(Epic epicNew) {
        if (!epics.containsKey(epicNew.getTaskId()) && epicNew == null)
            return;
        epics.put(epicNew.getTaskId(), epicNew);
    }

    @Override
    public void deleteEpic(int epicId) {
        if (!epics.containsKey(epicId))
            return;
        Epic epic = epics.get(epicId);
        for (Subtask subtask : epic.getEpicSublist()) {
            subtasks.remove(subtask.getTaskId());
            inMemoryHistoryManager.remove(subtask.getTaskId());
        }
        epics.remove(epicId);
        inMemoryHistoryManager.remove(epicId);

        /*Epic epic = epics.get(epicId);
        ArrayList<Integer> keyList = new ArrayList<>();
        for (Subtask subtask : epic.getEpicSublist()) {
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
        epics.remove(epicId);*/
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
    public void updateSubtask(Subtask subtaskNew) {
        if (!subtasks.containsKey(subtaskNew.getTaskId()) && subtaskNew == null)
            return;
        Subtask subtask = subtasks.get(subtaskNew.getTaskId());
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            int index = epic.getEpicSublist().indexOf(subtask);
            epic.getEpicSublist().set(index, subtaskNew);
            subtasks.put(subtaskNew.getTaskId(), subtaskNew);
            epic.setStatus();
        }
    }

    @Override
    public List<Subtask> getSubtasksByEpicId(int epicId) {
        if (epics.containsKey(epicId)) {
            Epic epic = epics.get(epicId);
            return epic.getEpicSublist();
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
            epic.getEpicSublist().remove(subtask);
            subtasks.remove(subtaskId);
            inMemoryHistoryManager.remove(subtaskId);
            epic.setStatus();
        }
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getEpicSublist().clear();
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

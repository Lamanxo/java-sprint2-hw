package manager;

import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

public class InMemoryTaskManager implements TaskManager {
    private int idGen;
    private HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private TreeSet<Task> prioritizedTasks = new TreeSet<>(new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            if (o1.getStartTime() == null || o2.getStartTime() == null) {
                return 1;
            } else {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        }
    });
    @Override
    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    public HistoryManager getInMemoryHistoryManager() {
        return inMemoryHistoryManager;
    }

    @Override
    public void setIdGen() {
        idGen++;
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
        if (!epics.containsKey(subtask.getEpicNumber()))
            return;
        if (noCrossingByTime.test(subtask)) {
            putSubtaskInEpic(subtask);
            setIdGen();
            subtask.setTaskId(idGen);
            subtasks.put(idGen, subtask);
            prioritizedTasks.add(subtask);
        }

    }

    @Override
    public void addTask(Task task) {
        if (task == null)
            return;
        if (noCrossingByTime.test(task)) {
            setIdGen();
            task.setTaskId(idGen);
            tasks.put(idGen, task);
            prioritizedTasks.add(task);
        }
    }

    private final Predicate<Task> noCrossingByTime = new Predicate<Task>() {
        @Override
        public boolean test(Task taskNew) {
            if (taskNew.getStartTime() == null) {
                return true;
            }
            LocalDateTime taskNewStartTime = taskNew.getStartTime();
            LocalDateTime taskNewEndTime = taskNew.getEndTime();
            for (Task taskFromSet : prioritizedTasks) {
                LocalDateTime taskStart = taskFromSet.getStartTime();
                LocalDateTime taskFinish = taskFromSet.getEndTime();
                if  (taskNewStartTime.isBefore(taskFinish) && taskNewEndTime.isAfter(taskFinish)) {
                    return false;
                }
                if  (taskNewStartTime.isBefore(taskStart) && taskNewEndTime.isAfter(taskStart)) {
                    return false;
                }
                if ((taskNewStartTime.isBefore(taskStart) && taskNewEndTime.isBefore(taskStart)) &&
                        (taskNewStartTime.isBefore(taskFinish) && taskNewEndTime.isBefore(taskFinish))) {
                    break;
                }
            }
            return true;
        }
    };

    private void putSubtaskInEpic(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicNumber());
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
            Epic epic = epics.get(subtask.getEpicNumber());
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
            Epic epic = epics.get(subtask.getEpicNumber());
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

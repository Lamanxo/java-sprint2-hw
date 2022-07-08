package manager;

import tasks.*;

import java.util.List;
import java.util.Set;

public interface TaskManager {

    void setIdGen();

    void addTask(Task task);

    Task getTask(int taskId);

    List<Task> getAllTasks();

    void updateTask(Task taskNew);

    void deleteTask(int taskId);

    void deleteAllTasks();

    void addEpic(Epic epic);

    Epic getEpic(int epicId);

    List<Epic> getAllEpics();

    void updateEpic(Epic epicNew);

    void deleteEpic(int epicId);

    void deleteAllEpics();

    void addSubtask(Subtask subtask);

    Subtask getSubtask(int sabtaskId);

    void updateSubtask(Subtask subtaskNew);

    List<Subtask> getSubtasksByEpicId(int epicId);

    List<Subtask> getAllSubtasks();

    void deleteSubtask(int subtaskId);

    void deleteAllSubtasks();

    List<Task> history();

    public Set<Task> getPrioritizedTasks();

}

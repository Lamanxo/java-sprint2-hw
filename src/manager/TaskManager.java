package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public interface TaskManager {

    void addTask(Task task);

    void addSubTask(SubTask subtask);

    void addTaskToEpic(Epic epic, long idTask);

    void updateEpic(Epic epic);

    void printAllTasks();

    void printAllEpics();

    void printAllSubTasks();

    void printAllSubTasksByEpic(long taskId);

    void deleteAllTasks();

    void removeTaskById(long taskId);

    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

}

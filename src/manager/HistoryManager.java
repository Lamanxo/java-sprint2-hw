package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {
    ArrayList<Task> viewedTasks = new ArrayList<>();
    void add(Task task);

    void getHistory();
}

package manager;

import tasks.Task;
import java.util.LinkedList;
import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {
    LinkedList<Task> viewedTasks = new LinkedList<>();
    private final int maxViewedTasks = 10;

    @Override
    public void add(Task task) {
        if (viewedTasks.size() >= maxViewedTasks) {
            viewedTasks.remove(0);
            viewedTasks.add(task);
        } else {
            viewedTasks.add(task);
        }
    }

    public List<Task> getHistory() {
        return viewedTasks;
    }


}
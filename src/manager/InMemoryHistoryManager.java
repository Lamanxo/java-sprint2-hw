package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {




    @Override
    public void add(Task task) {
        if (viewedTasks.size() >= 10) {
            viewedTasks.remove(0);
            viewedTasks.add(task);
        } else {
            viewedTasks.add(task);
        }
    }

    @Override
    public void getHistory() {
        if (!viewedTasks.isEmpty()) {
            System.out.println("Все просмотренные задачи:");
            for (Task taskSout : viewedTasks) {
                System.out.println(taskSout);
            }
        } else {
            System.out.println("Просмотренных задач не обнаружено");
        }
    }
}
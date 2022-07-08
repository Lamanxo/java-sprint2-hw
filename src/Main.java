import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        TaskManager tm = Managers.getDefault();
        Epic epic1 = new Epic("epic", "epicDesk");
        Subtask st1 = new Subtask("sub1", "subd1", Status.NEW, 1, LocalDateTime.now(), Duration.ofHours(22));
        Subtask st2 = new Subtask("sub111", "subd1111", Status.NEW, 1, LocalDateTime.now().plusHours(22), Duration.ofHours(22));

        Task task = new Task("Task1", "desc1",Status.NEW,LocalDateTime.now().plusHours(48),Duration.ofHours(23));

        tm.addEpic(epic1);
        tm.addSubtask(st1);
        tm.addSubtask(st2);
        tm.getSubtask(2);
        tm.getSubtask(3);
        tm.addTask(task);
        System.out.println(tm.history().size());
        tm.history().forEach(System.out::println);
        System.out.println("ffff");
        tm.getPrioritizedTasks().forEach(System.out::println);
        System.out.println("ffff");


        System.out.println(tm.getAllEpics());
        System.out.println(tm.getAllSubtasks());
        System.out.println(tm.getAllTasks());



    }
}

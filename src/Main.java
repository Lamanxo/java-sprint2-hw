import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;


public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        TaskManager tm = Managers.getDefault();
        Epic epic1 = new Epic("epic", "epicDesk", Status.NEW);
        Subtask st1 = new Subtask("sub1", "subd1", Status.NEW, 1);


        tm.addEpic(epic1);
        tm.addSubtask(st1);
        Subtask st1u = new Subtask(2,"sub1", "subd1", Status.IN_PROGRESS, 1);
        tm.updateSubtask(st1u);
        System.out.println(tm.getAllEpics());
        System.out.println(tm.getAllSubtasks());



    }
}

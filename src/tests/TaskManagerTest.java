package tests;

import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;
import tasks.Status;
import tasks.Task;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static tasks.Status.*;

public class TaskManagerTest {
    // protected T taskManager;


    TaskManager taskManager = Managers.getDefault();

    //InMemoryTaskManager taskManager = new InMemoryTaskManager();

    //public abstract void initializeTaskManager();

    //@BeforeEach
    //public abstract void beforeEach();

    /*@Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW);
        taskManager.addTask(task);
        final int taskId = task.getTaskId();
        final Task savedTask = taskManager.getTask(taskId);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }*/

    @Test
    public void addUpdateAndDeleteSimpleTaskInOrdinaryWorkTaskManagerTest() {
        taskManager.addTask(new Task ("task 1", "desc 1", Status.NEW));
        Task task = taskManager.getTask(1);
        assertEquals(task.getTaskId(), 1);
        assertEquals(taskManager.getTask(1).getStatus(), Status.NEW);
        taskManager.updateTask(new Task (1, "task 1", "desc 1", Status.DONE));
        assertEquals(taskManager.getTask(1).getStatus(), Status.DONE);
        taskManager.deleteTask(1);
        assertNull(taskManager.getTask(1), "В трекере задач нет задач");
    }

    @Test
    public void addUpdateAndDeleteEpicInOrdinaryWorkTaskManagerTest() {
        taskManager.addEpic(new Epic ("epic 1", "desc 1", Status.NEW));
        Epic epic = taskManager.getEpic(1);
        assertEquals(epic.getTaskId(), 1);
        taskManager.updateEpic(new Epic (1,"epic 1", "desc 1", Status.DONE));
        assertEquals(taskManager.getEpic(1).getStatus(), Status.NEW);
        taskManager.deleteEpic(1);
        assertNull(taskManager.getEpic(1), "В трекере задач нет задач");
    }

    @Test
    public void addUpdateAndDeleteSubtaskInOrdinaryWorkTaskManagerTest() {
        taskManager.addEpic(new Epic ("epic 1", "desc 1", Status.NEW));
        taskManager.addSubtask(new Subtask("subtask 1.1", "desc 1.1", Status.NEW, 1));

        assertEquals(taskManager.getEpic(1).getEpicSublist().size(), 1);
        assertEquals(taskManager.getSubtask(2).getStatus(), Status.NEW);
        assertEquals(taskManager.getSubtask(2).getEpicNumber(), 1);
        taskManager.updateSubtask(new Subtask("subtask 1.1", "desc 1.1", Status.IN_PROGRESS, 1));
        assertEquals(taskManager.getEpic(1).getStatus(), Status.IN_PROGRESS);
        assertEquals(taskManager.getSubtask(1).getStatus(), Status.IN_PROGRESS);
        taskManager.deleteSubtask(1);
        assertNull(taskManager.getSubtask(1), "В трекере задач нет задач");
    }

    /*@Test
    public void changeEpicStatusInOrdinaryWorkTaskManagerTest() {
        taskManager.addEpic(new Epic ("epic 1", "desc 1", TaskStatus.NEW));
        taskManager.addSubtask(0, new Subtask("subtask 1.1", "desc 1.1", TaskStatus.NEW));
        taskManager.addSubtask(0, new Subtask("subtask 1.2", "desc 1.2", TaskStatus.NEW));

        assertEquals(taskManager.getEpic(0).getSubtasks().size(), 2);
        assertEquals(taskManager.getEpic(0).getStatus(), TaskStatus.NEW);
        taskManager.updateSubtaskById(new Subtask(1,"subtask 1.1", "desc 1.1", TaskStatus.IN_PROGRESS));
        assertEquals(taskManager.getEpic(0).getStatus(), TaskStatus.IN_PROGRESS);
        taskManager.updateSubtaskById(new Subtask(2,"subtask 1.2", "desc 1.2", TaskStatus.DONE));
        assertEquals(taskManager.getEpic(0).getStatus(), TaskStatus.IN_PROGRESS);
        taskManager.updateSubtaskById(new Subtask(1,"subtask 1.1", "desc 1.1", TaskStatus.DONE));
        assertEquals(taskManager.getEpic(0).getStatus(), TaskStatus.DONE);
        taskManager.deleteAllTasks();
        assertNull(taskManager.getSubtask(1), "В трекере задач нет задач");
        assertNull(taskManager.getSubtask(2), "В трекере задач нет задач");
        assertNull(taskManager.getEpic(0), "В трекере задач нет задач");
    }

    @Test
    public void getHistoryInOrdinaryWorkTaskManagerTest() {
        taskManager.addSimpleTask(new SimpleTask ("task 1", "desc 1", TaskStatus.IN_PROGRESS));
        taskManager.addEpic(new Epic ("epic 1", "desc 1", TaskStatus.NEW));
        taskManager.addSubtask(1, new Subtask("subtask 1.1", "desc 1.1", TaskStatus.DONE));
        taskManager.getEpic(1);
        taskManager.getSimpleTask(0);
        taskManager.getSubtask(2);
        taskManager.getEpic(1);
        List<Integer> id = Arrays.asList(0, 2 ,1);
        assertEquals (taskManager.history().stream().map(Task::getId).collect(Collectors.toList()), id);
    }
*/


}

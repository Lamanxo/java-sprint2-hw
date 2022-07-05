package manager;

import manager.Managers;
import manager.TaskManager;
import tasks.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static tasks.Status.*;

abstract class TaskManagerTest<T extends TaskManager> {
     private T taskManager;

    //TaskManager taskManager = Managers.getDefault();


    public void setTaskManager(T taskManager) {
        this.taskManager = taskManager;
    }

    @Test
    public void addUpdateAndDeleteTaskTest() {
        taskManager.addTask(new Task ("task1", "task1Desc", Status.NEW));
        Task task = taskManager.getTask(1);
        assertNotNull(task, "Задачи из менеджера не возвращаются.");
        assertEquals(task.getTaskId(), 1, "Задачи по ID не совпадают");
        assertEquals(taskManager.getTask(1).getStatus(), Status.NEW, "Статусы не совпадают");
        taskManager.updateTask(new Task (1, "task1", "task1Desc", Status.DONE));
        assertEquals(taskManager.getTask(1).getStatus(), Status.DONE, "Метод обновления не работает");
        final List<Task> tasks = taskManager.getAllTasks();
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        taskManager.deleteTask(1);
        assertNull(taskManager.getTask(1), "Трекер не пустой");
        final List<Task> emptyTasks = taskManager.getAllTasks();
        assertEquals(0,emptyTasks.size(),"Трекер не пустой");
    }


    @Test
    public void addUpdateAndDeleteEpicTest() {
        taskManager.addEpic(new Epic ("epic1", "epic1Desc", Status.NEW));
        Epic epic = taskManager.getEpic(1);
        assertNotNull(epic, "Эпики не возвращаются.");
        assertEquals(epic.getTaskId(), 1, "Эпики по ID не совпадают");
        taskManager.updateEpic(new Epic (1,"epic1_upd", "epic1Desc_upd", Status.NEW));
        assertEquals(taskManager.getEpic(1).getStatus(), Status.NEW,"Метод обновления не работает");
        final List<Epic> epics = taskManager.getAllEpics();
        assertEquals(1, epics.size(), "Неверное количество задач.");
        taskManager.deleteEpic(1);
        assertNull(taskManager.getEpic(1), "Трекер не пустой");
        final List<Epic> emptyEpics = taskManager.getAllEpics();
        assertEquals(0,emptyEpics.size(),"Трекер не пустой");

    }


    @Test
    public void addUpdateAndDeleteSubtaskTest() {
        taskManager.addEpic(new Epic ("epic1", "epic1Desc", Status.NEW));
        taskManager.addSubtask(new Subtask("subtask1", "subtask1Desc", Status.NEW, 1));
        Subtask sub = taskManager.getSubtask(2);
        assertNotNull(sub,"Сабы не возвращаются.");
        assertEquals(taskManager.getEpic(1).getEpicSublist().size(), 1,"Сабы не сохраняются в Эпики");
        assertEquals(taskManager.getSubtask(2).getStatus(), Status.NEW,"Статус не соответствует");
        assertEquals(taskManager.getSubtask(2).getEpicNumber(), 1,"Сабтаск не соответствует Эпику");
        taskManager.updateSubtask(new Subtask(2,"subtask 1.1_updated", "desc 1.1", IN_PROGRESS, 1));
        assertEquals(Status.IN_PROGRESS, taskManager.getEpic(1).getStatus(),"Статус Эпика не обновляется");
        assertEquals(taskManager.getSubtask(2).getStatus(), Status.IN_PROGRESS, "Метод обновления Саба не работает");
        taskManager.deleteSubtask(2);
        assertNull(taskManager.getSubtask(2), "Трекер не пустой");
        taskManager.addEpic(new Epic ("epic1", "epic1Desc", Status.NEW));
        taskManager.addSubtask(new Subtask("subtask1", "subtask1Desc", Status.NEW, 1));
        final List<Subtask> subs = taskManager.getAllSubtasks();
        assertEquals(1,subs.size(),"Метод не возвращает Сабы");
        taskManager.deleteAllSubtasks();
        final List<Subtask> subsEmpty = taskManager.getAllSubtasks();
        assertEquals(subsEmpty.size(),0,"Метод не удаляет Сабы");


    }

    @Test
    public void getHistoryTest() {
        taskManager.addTask(new Task ("task1", "task1Desc", Status.IN_PROGRESS));
        taskManager.addEpic(new Epic ("epic1", "epic1Desc", Status.NEW));
        taskManager.addSubtask(new Subtask("subtask1", "subtask1Desc", Status.DONE, 2));
        taskManager.getEpic(2);
        taskManager.getTask(1);
        taskManager.getSubtask(3);
        taskManager.getEpic(2);
        List<Integer> id = Arrays.asList(1, 3 ,2);
        assertEquals (taskManager.history().stream().map(Task::getTaskId).collect(Collectors.toList()), id);
    }



}

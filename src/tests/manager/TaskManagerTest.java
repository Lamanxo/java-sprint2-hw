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
        assertNotNull(task, "������ �� ��������� �� ������������.");
        assertEquals(task.getTaskId(), 1, "������ �� ID �� ���������");
        assertEquals(taskManager.getTask(1).getStatus(), Status.NEW, "������� �� ���������");
        taskManager.updateTask(new Task (1, "task1", "task1Desc", Status.DONE));
        assertEquals(taskManager.getTask(1).getStatus(), Status.DONE, "����� ���������� �� ��������");
        final List<Task> tasks = taskManager.getAllTasks();
        assertEquals(1, tasks.size(), "�������� ���������� �����.");
        taskManager.deleteTask(1);
        assertNull(taskManager.getTask(1), "������ �� ������");
        final List<Task> emptyTasks = taskManager.getAllTasks();
        assertEquals(0,emptyTasks.size(),"������ �� ������");
    }


    @Test
    public void addUpdateAndDeleteEpicTest() {
        taskManager.addEpic(new Epic ("epic1", "epic1Desc", Status.NEW));
        Epic epic = taskManager.getEpic(1);
        assertNotNull(epic, "����� �� ������������.");
        assertEquals(epic.getTaskId(), 1, "����� �� ID �� ���������");
        taskManager.updateEpic(new Epic (1,"epic1_upd", "epic1Desc_upd", Status.NEW));
        assertEquals(taskManager.getEpic(1).getStatus(), Status.NEW,"����� ���������� �� ��������");
        final List<Epic> epics = taskManager.getAllEpics();
        assertEquals(1, epics.size(), "�������� ���������� �����.");
        taskManager.deleteEpic(1);
        assertNull(taskManager.getEpic(1), "������ �� ������");
        final List<Epic> emptyEpics = taskManager.getAllEpics();
        assertEquals(0,emptyEpics.size(),"������ �� ������");

    }


    @Test
    public void addUpdateAndDeleteSubtaskTest() {
        taskManager.addEpic(new Epic ("epic1", "epic1Desc", Status.NEW));
        taskManager.addSubtask(new Subtask("subtask1", "subtask1Desc", Status.NEW, 1));
        Subtask sub = taskManager.getSubtask(2);
        assertNotNull(sub,"���� �� ������������.");
        assertEquals(taskManager.getEpic(1).getEpicSublist().size(), 1,"���� �� ����������� � �����");
        assertEquals(taskManager.getSubtask(2).getStatus(), Status.NEW,"������ �� �������������");
        assertEquals(taskManager.getSubtask(2).getEpicNumber(), 1,"������� �� ������������� �����");
        taskManager.updateSubtask(new Subtask(2,"subtask 1.1_updated", "desc 1.1", IN_PROGRESS, 1));
        assertEquals(Status.IN_PROGRESS, taskManager.getEpic(1).getStatus(),"������ ����� �� �����������");
        assertEquals(taskManager.getSubtask(2).getStatus(), Status.IN_PROGRESS, "����� ���������� ���� �� ��������");
        taskManager.deleteSubtask(2);
        assertNull(taskManager.getSubtask(2), "������ �� ������");
        taskManager.addEpic(new Epic ("epic1", "epic1Desc", Status.NEW));
        taskManager.addSubtask(new Subtask("subtask1", "subtask1Desc", Status.NEW, 1));
        final List<Subtask> subs = taskManager.getAllSubtasks();
        assertEquals(1,subs.size(),"����� �� ���������� ����");
        taskManager.deleteAllSubtasks();
        final List<Subtask> subsEmpty = taskManager.getAllSubtasks();
        assertEquals(subsEmpty.size(),0,"����� �� ������� ����");


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

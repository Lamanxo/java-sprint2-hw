package tests;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import tasks.*;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    Subtask subtask = new Subtask("Subtask-1", "0", Status.NEW, 1);
    Subtask subtask1 = new Subtask("Subtask-1", "0", Status.NEW, 1);
    Epic epic = new Epic("Epic_TEST", "TEST");

    @Test
    void test1_ShoulEpicStattusWithOutSubtasks() {
        inMemoryTaskManager.addEpic(epic);
        assertEquals(Status.NEW, inMemoryTaskManager.getEpic(1).getStatus());
    }

    @Test
    void test2_EpicStattusWithSubtasksNew() {
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSubtask(subtask);
        inMemoryTaskManager.addSubtask(subtask1);
        assertEquals(Status.NEW, inMemoryTaskManager.getEpic(1).getStatus());
    }

    @Test
    void test3_EpicStatusWithSubtasksDone(){
        inMemoryTaskManager.addEpic(epic);
        subtask.setStatus(Status.DONE);
        subtask1.setStatus(Status.DONE);
        inMemoryTaskManager.addSubtask(subtask);
        inMemoryTaskManager.addSubtask(subtask1);
        assertEquals(Status.DONE, inMemoryTaskManager.getEpic(1).getStatus());
    }

    @Test
    void test4_EpicStatusWithSubtasksNewAndDone(){
        inMemoryTaskManager.addEpic(epic);
        subtask1.setStatus(Status.DONE);
        inMemoryTaskManager.addSubtask(subtask);
        inMemoryTaskManager.addSubtask(subtask1);
        assertEquals(Status.IN_PROGRESS, inMemoryTaskManager.getEpic(1).getStatus());
    }

    @Test
    void test5_EpicStatusWithSubtasksInProgress(){
        inMemoryTaskManager.addEpic(epic);
        subtask.setStatus(Status.IN_PROGRESS);
        subtask1.setStatus(Status.IN_PROGRESS);
        inMemoryTaskManager.addSubtask(subtask);
        inMemoryTaskManager.addSubtask(subtask1);
        assertEquals(Status.IN_PROGRESS, inMemoryTaskManager.getEpic(1).getStatus());
    }
}
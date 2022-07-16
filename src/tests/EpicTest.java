import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import tasks.*;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    InMemoryTaskManager testManager = new InMemoryTaskManager();

    Epic epic = new Epic("EpicTest", "DescriptionEpicTest");
    Subtask testSubtask1 = new Subtask("testSubstaskName1", "testSubDesc1", Status.NEW, 1);
    Subtask testSubstask2 = new Subtask("testSubtaskName2", "testSubDesc2", Status.NEW, 1);


    @Test
    public void epicNewWithOutSubtasks() {
        testManager.addEpic(epic);
        assertEquals(Status.NEW, testManager.getEpic(1).getStatus());
    }

    @Test
    public void epicNewWithSubtasksNew() {
        testManager.addEpic(epic);
        testManager.addSubtask(testSubtask1);
        testManager.addSubtask(testSubstask2);
        assertEquals(Status.NEW, testManager.getEpic(1).getStatus());
    }

    @Test
    public void epicDoneWithSubtasksDone(){
        testManager.addEpic(epic);
        testSubtask1.setStatus(Status.DONE);
        testSubstask2.setStatus(Status.DONE);
        testManager.addSubtask(testSubtask1);
        testManager.addSubtask(testSubstask2);
        assertEquals(Status.DONE, testManager.getEpic(1).getStatus());
    }

    @Test
    public void epicInProgressWithSubtasksNewAndDone(){
        testManager.addEpic(epic);
        testSubstask2.setStatus(Status.DONE);
        testManager.addSubtask(testSubtask1);
        testManager.addSubtask(testSubstask2);
        assertEquals(Status.IN_PROGRESS, testManager.getEpic(1).getStatus());
    }

    @Test
    public void epicInProgressWithSubtasksInProgress(){
        testManager.addEpic(epic);
        testSubtask1.setStatus(Status.IN_PROGRESS);
        testSubstask2.setStatus(Status.IN_PROGRESS);
        testManager.addSubtask(testSubtask1);
        testManager.addSubtask(testSubstask2);
        assertEquals(Status.IN_PROGRESS, testManager.getEpic(1).getStatus());
    }
}
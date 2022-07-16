package manager;

import org.junit.jupiter.api.Test;
import tasks.*;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagerTest {
    private InMemoryTaskManager inMemtaskMan = new InMemoryTaskManager();
    private Epic epic1 = new Epic("Epic1Test", "Epic1TestDesc");
    private Subtask subtask1 = new Subtask("Subtask1Test", "Subtask1TestDesc", Status.NEW, 1);
    private Task task1 = new Task("Task1Test", "Task1TestDesc", Status.NEW);

    @Test
    public void checkEmptyHistory() {

        assertTrue(inMemtaskMan.history().isEmpty());
    }

    @Test
    public void deleteHistoryAtBeginning() {
        inMemtaskMan.addEpic(epic1);
        inMemtaskMan.addSubtask(subtask1);
        inMemtaskMan.addTask(task1);
        inMemtaskMan.getTask(3);
        inMemtaskMan.getEpic(1);
        inMemtaskMan.getSubtask(2);
        inMemtaskMan.getInMemoryHistoryManager().remove(1);
        assertEquals(2, inMemtaskMan.history().size());
    }

    @Test
    public void deleteHistoryInMiddle() {
        inMemtaskMan.addEpic(epic1);
        inMemtaskMan.addSubtask(subtask1);
        inMemtaskMan.addTask(task1);
        inMemtaskMan.getTask(3);
        inMemtaskMan.getEpic(1);
        inMemtaskMan.getSubtask(2);
        inMemtaskMan.getInMemoryHistoryManager().remove(2);
        assertEquals(2, inMemtaskMan.history().size());
    }

    @Test
    public void deleteHistoryAtEnd() {
        inMemtaskMan.addEpic(epic1);
        inMemtaskMan.addSubtask(subtask1);
        inMemtaskMan.addTask(task1);
        inMemtaskMan.getTask(3);
        inMemtaskMan.getEpic(1);
        inMemtaskMan.getSubtask(2);
        inMemtaskMan.getInMemoryHistoryManager().remove(3);
        assertEquals(2, inMemtaskMan.history().size());
    }

    @Test
    public void checkForDoublesInHistory() {
        inMemtaskMan.addEpic(epic1);
        inMemtaskMan.addSubtask(subtask1);
        inMemtaskMan.addTask(task1);
        inMemtaskMan.getTask(3);
        inMemtaskMan.getTask(3);
        inMemtaskMan.getEpic(1);
        assertEquals(2, inMemtaskMan.history().size());
    }
}
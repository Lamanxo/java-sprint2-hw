package manager;

import org.junit.jupiter.api.Test;
import tasks.*;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagerTest {
    private InMemoryTaskManager inMemtaskMan = new InMemoryTaskManager();
    private Epic epic1 = new Epic("Epic1Test", "Epic1TestDesc");
    private Subtask subtask1 = new Subtask("Subtask1Test", "Subtask1TestDesc", Status.NEW, 2);
    private Task task1 = new Task("Task1Test", "Task1TestDesc", Status.NEW);

    @Test
    public void CheckEmptyHistory() {

        assertTrue(inMemtaskMan.history().isEmpty());
    }

    @Test
    public void CheckForDoublesInHistory() {
        inMemtaskMan.addEpic(epic1);
        inMemtaskMan.addSubtask(subtask1);
        inMemtaskMan.addTask(task1);
        inMemtaskMan.getTask(1);
        inMemtaskMan.getTask(1);
        inMemtaskMan.getEpic(2);
        assertEquals(2, inMemtaskMan.history().size());
    }

    @Test
    public void DeleteHistoryAtBeginning() {
        inMemtaskMan.addEpic(epic1);
        inMemtaskMan.addSubtask(subtask1);
        inMemtaskMan.addTask(task1);
        inMemtaskMan.getTask(1);
        inMemtaskMan.getEpic(2);
        inMemtaskMan.getSubtask(3);
        inMemtaskMan.getInMemoryHistoryManager().remove(1);
        assertEquals(2, inMemtaskMan.history().size());
    }

    @Test
    public void DeleteHistoryInMiddle() {
        inMemtaskMan.addEpic(epic1);
        inMemtaskMan.addSubtask(subtask1);
        inMemtaskMan.addTask(task1);
        inMemtaskMan.getTask(1);
        inMemtaskMan.getEpic(2);
        inMemtaskMan.getSubtask(3);
        inMemtaskMan.getInMemoryHistoryManager().remove(2);
        assertEquals(2, inMemtaskMan.history().size());
    }

    @Test
    public void DeleteHistoryAtEnd() {
        inMemtaskMan.addEpic(epic1);
        inMemtaskMan.addSubtask(subtask1);
        inMemtaskMan.addTask(task1);
        inMemtaskMan.getTask(1);
        inMemtaskMan.getEpic(2);
        inMemtaskMan.getSubtask(3);
        inMemtaskMan.getInMemoryHistoryManager().remove(3);
        assertEquals(2, inMemtaskMan.history().size());
    }
}
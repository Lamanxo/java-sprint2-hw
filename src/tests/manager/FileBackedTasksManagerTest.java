package manager;

import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Task;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest <FileBackedTasksManager> {


    @Test
    public void CheckForEpicsWithoutSubs() {
        FileBackedTasksManager fbBefore = new FileBackedTasksManager("testFile/epicTest.csv");
        fbBefore.addEpic(new Epic("Epic1", "epic1Desc"));
        fbBefore.addEpic(new Epic("Epic2", "epic2Desc"));
        FileBackedTasksManager fbAfter = FileBackedTasksManager.loadFromFile(new File("testFile/epicTest.csv"));
        assertEquals(fbAfter.getAllEpics().size(), fbBefore.getAllEpics().size());
    }

    @Test
    public void CheckForEmptyFBTaskManager() {
        FileBackedTasksManager fbBefore1 = new FileBackedTasksManager("testFile/emptyTest.csv");
        fbBefore1.save();
        FileBackedTasksManager fbAfter1 = FileBackedTasksManager.loadFromFile(new File("testFile/emptyTest.csv"));
        assertEquals(fbBefore1.getAllTasks().size(), fbAfter1.getAllTasks().size());
        assertEquals(fbBefore1.getAllEpics().size(), fbAfter1.getAllEpics().size());
        assertEquals(fbBefore1.getAllSubtasks().size(), fbAfter1.getAllSubtasks().size());

    }

    @Test
    public void CheckForEmptyHistory() {
        FileBackedTasksManager fbBefore2 = new FileBackedTasksManager("testFile/emptyHistoryTest.csv");
        fbBefore2.addEpic(new Epic("Epic1", "epic1Desc"));
        fbBefore2.addEpic(new Epic("Epic2", "epic2Desc"));
        fbBefore2.addTask(new Task("Task","TaskDesc"));
        FileBackedTasksManager fbAfter2 = FileBackedTasksManager.loadFromFile(new File("testFile/emptyHistoryTest.csv"));
        assertEquals(fbAfter2.history().size(),fbBefore2.history().size());
    }
}
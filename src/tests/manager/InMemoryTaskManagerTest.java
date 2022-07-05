package manager;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    public InMemoryTaskManagerTest() {

        setTaskManager(new InMemoryTaskManager());
    }
}
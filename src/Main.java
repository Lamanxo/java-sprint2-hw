import Manager.TaskManager;
import Tasks.Epic;
import Tasks.Status;
import Tasks.SubTask;
import Tasks.Task;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        int idGenerator = 1;
        TaskManager taskManager = new TaskManager();


        Task task1 = new Task(idGenerator, "Обычная задача" , "Сходить в магазин", Status.NEW);
        idGenerator++;
        taskManager.addTask(task1);

        Task task2 = new Task(idGenerator, "Обычная задача" , "Сходить в магазин", Status.NEW);
        idGenerator++;
        taskManager.addTask(task2);

        Epic epic1 = new Epic(idGenerator, "Переезд" , "Смена квартиры");
        idGenerator++;
        taskManager.addEpic(epic1);

        SubTask subt1 = new SubTask(idGenerator, "Собрать вещи", "Разобрать мебель, картины"
                , Status.NEW, 3);
        idGenerator++;
        taskManager.addSubTask(subt1);

        SubTask subt2 = new SubTask(idGenerator, "Транспорт", "Найти адекватного водителя"
                , Status.NEW, 3);
        idGenerator++;
        taskManager.addSubTask(subt2);

        taskManager.printAllSubTasksByEpic(3);
        taskManager.printAll();
        taskManager.getTaskById(2);

        Task task3 = new Task(2, "Обновленная обычная задача", "Сходить в хозмаг", Status.NEW);

        /*taskManager.updateTask(task3);
        taskManager.printAll();
        taskManager.removeTaskbyId(1);
        taskManager.printAll();*/
 /*
        ДЛЯ КАЖДОГО ИЗ ТИПА ЗАДАЧ
                printAllTasks
                deleteAllTasks
                getTaskbyId
                addTask(внутри обьект как параметр)
                updateTask(передача в виде параметра)
                deleteTaskByID

                 getSubTasksOfEpicByID
                        */
    }
}

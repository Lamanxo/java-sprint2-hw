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

        Epic epic2 = new Epic(idGenerator, "ДР" , "Запланировать ДР");
        idGenerator++;
        taskManager.addEpic(epic2);

        SubTask subt3 = new SubTask(idGenerator, "Купить торт", "Я несу тортик...", Status.NEW, 6);
        idGenerator++;
        taskManager.addSubTask(subt3);

        System.out.println("_________________________Проверка печати всех задач________________________________");
        taskManager.printAll();

        System.out.println("_________________________Проверка печати по id задачи______________________________");
        taskManager.getTaskById(4);
        taskManager.getTaskById(1);
        taskManager.getTaskById(6);

        System.out.println("_________________________Проверка метода update____________________________________");
        SubTask subt11 = new SubTask(4, "Собрать вещи", "Разобрать мебель, картины"
                , Status.DONE, 3);
        taskManager.updateSubTask(subt11);
        taskManager.printAll();

        System.out.println("_________________________Проверка метода update Epic_______________________________");
        SubTask subt12 = new SubTask(5, "Транспорт", "Найти адекватного водителя"
                , Status.DONE, 3);
        taskManager.updateSubTask(subt12);
        taskManager.printAll();

        System.out.println("_________________________Проверка подзадач определенного Эпика______________________");
        taskManager.printAllSubTasksByEpic(3);

        System.out.println("_________________________Удаляем Эпик и проверяем удаление подзадач_________________");
        taskManager.removeTaskbyId(3);
        taskManager.printAll();

        System.out.println("_________________________Удаляем подзадачу и проверяем Эпик_________________________");
        taskManager.removeTaskbyId(6);
        taskManager.printAll();







    }
}

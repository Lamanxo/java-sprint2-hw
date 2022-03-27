import manager.InMemoryTaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();


        Task task1 = new Task(inMemoryTaskManager.getNewId(), "Обычная задача", "Сходить в магазин", Status.NEW);

        inMemoryTaskManager.addTask(task1);

        Task task2 = new Task(inMemoryTaskManager.getNewId(), "Обычная задача", "Сходить в магазин", Status.NEW);

        inMemoryTaskManager.addTask(task2);

        Epic epic1 = new Epic(inMemoryTaskManager.getNewId(), "Переезд", "Смена квартиры");

        inMemoryTaskManager.addEpic(epic1);

        SubTask subt1 = new SubTask(inMemoryTaskManager.getNewId(), "Собрать вещи", "Разобрать мебель, картины"
                , Status.NEW, 3);

        inMemoryTaskManager.addSubTask(subt1);

        SubTask subt2 = new SubTask(inMemoryTaskManager.getNewId(), "Транспорт", "Найти адекватного водителя"
                , Status.NEW, 3);

        inMemoryTaskManager.addSubTask(subt2);

        SubTask subt3 = new SubTask(inMemoryTaskManager.getNewId(), "Нанять сборщиков", "Найти пряморуких сборщиков мебели"
                , Status.NEW, 3);

        inMemoryTaskManager.addSubTask(subt3);

        Epic epic2 = new Epic(inMemoryTaskManager.getNewId(), "ДР", "Запланировать ДР");

        inMemoryTaskManager.addEpic(epic2);


        System.out.println("_________________________Проверка печати всех задач________________________________");
        inMemoryTaskManager.printAll();

        System.out.println("_________________________Проверка печати по id задачи______________________________");
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getTaskById(5);
        inMemoryTaskManager.getTaskById(6);
        inMemoryTaskManager.getTaskById(3);
        inMemoryTaskManager.getTaskById(4);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getTaskById(7);
        System.out.println("_________________________Проверка метода истории просмотра__________________________");
        System.out.println(inMemoryTaskManager.history());
        System.out.println("_________________________Удаляем задачу из истории__________________________________");
        inMemoryTaskManager.removeTaskById(1);
        System.out.println(inMemoryTaskManager.history());
        System.out.println("_________________________Удаляем Эпик и подзадачи из истории________________________");
        inMemoryTaskManager.removeTaskById(3);
        System.out.println(inMemoryTaskManager.history());
    }
}

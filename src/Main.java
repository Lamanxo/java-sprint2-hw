import manager.InMemoryTaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Task task1 = new Task(inMemoryTaskManager.getNewId(), "Обычная задача" , "Сходить в магазин", Status.NEW);
        
        inMemoryTaskManager.addTask(task1);

        Task task2 = new Task(inMemoryTaskManager.getNewId(), "Обычная задача" , "Сходить в магазин", Status.NEW);
        
        inMemoryTaskManager.addTask(task2);

        Epic epic1 = new Epic(inMemoryTaskManager.getNewId(), "Переезд" , "Смена квартиры");
        
        inMemoryTaskManager.addEpic(epic1);

        SubTask subt1 = new SubTask(inMemoryTaskManager.getNewId(), "Собрать вещи", "Разобрать мебель, картины"
                , Status.NEW, 3);
        
        inMemoryTaskManager.addSubTask(subt1);

        SubTask subt2 = new SubTask(inMemoryTaskManager.getNewId(), "Транспорт", "Найти адекватного водителя"
                , Status.NEW, 3);
        
        inMemoryTaskManager.addSubTask(subt2);

        Epic epic2 = new Epic(inMemoryTaskManager.getNewId(), "ДР" , "Запланировать ДР");
        
        inMemoryTaskManager.addEpic(epic2);

        SubTask subt3 = new SubTask(inMemoryTaskManager.getNewId(), "Купить торт", "Я несу тортик...", Status.NEW, 6);
        
        inMemoryTaskManager.addSubTask(subt3);

        System.out.println("_________________________Проверка печати всех задач________________________________");
        inMemoryTaskManager.printAll();

        System.out.println("_________________________Проверка печати по id задачи______________________________");
        inMemoryTaskManager.getTaskById(4);
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(6);

        System.out.println("_________________________Проверка метода update____________________________________");
        SubTask subt11 = new SubTask(4, "Собрать вещи", "Разобрать мебель, картины"
                , Status.DONE, 3);
        inMemoryTaskManager.updateSubTask(subt11);
        inMemoryTaskManager.printAll();

        System.out.println("_________________________Проверка метода update Epic_______________________________");
        SubTask subt12 = new SubTask(5, "Транспорт", "Найти адекватного водителя"
                , Status.DONE, 3);
        inMemoryTaskManager.updateSubTask(subt12);
        inMemoryTaskManager.printAll();

        System.out.println("_________________________Проверка подзадач определенного Эпика______________________");
        inMemoryTaskManager.printAllSubTasksByEpic(3);

        System.out.println("_________________________Удаляем Эпик и проверяем удаление подзадач_________________");
        inMemoryTaskManager.removeTaskById(3);
        inMemoryTaskManager.printAll();

        System.out.println("_________________________Удаляем подзадачу и проверяем Эпик_________________________");
        inMemoryTaskManager.removeTaskById(6);
        inMemoryTaskManager.printAll();

    }
}

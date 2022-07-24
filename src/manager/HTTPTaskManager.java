
package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import httpserver.KVTaskClient;
import com.google.gson.reflect.TypeToken;
import tasks.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HTTPTaskManager extends FileBackedTasksManager {
        private String url;
        private KVTaskClient client;
        private Gson gson = getGson();

        public HTTPTaskManager(String url) {
            this.client = new KVTaskClient(url);
        }

        public static Gson getGson() {
            GsonBuilder gsonBuilder = new GsonBuilder();
            return gsonBuilder.create();
        }

    public void load() {
        try {
            String jsonTasks = client.load("TASK");
            final HashMap<Integer, Task> restoredTasks = gson.fromJson(jsonTasks,
                    new TypeToken<HashMap<Integer, Task>>() {
                    }.getType());
            for (Map.Entry<Integer, Task> taskEntry : restoredTasks.entrySet()) {
                tasks.put(taskEntry.getKey(), taskEntry.getValue());
            }
            String jsonEpics = client.load("EPIC");
            final HashMap<Integer, Epic> restoredEpics = gson.fromJson(jsonEpics,
                    new TypeToken<HashMap<Integer, Epic>>() {
                    }.getType());
            for (Map.Entry<Integer, Epic> epicEntry : restoredEpics.entrySet()) {
                epics.put(epicEntry.getKey(), epicEntry.getValue());
            }
            String jsonSubtasks = client.load("SUBTASK");
            final HashMap<Integer, Subtask> restoredSubtasks = gson.fromJson(jsonSubtasks,
                    new TypeToken<HashMap<Integer, Subtask>>() {
                    }.getType());
            for (Map.Entry<Integer, Subtask> subtaskEntry : restoredSubtasks.entrySet()) {
                subtasks.put(subtaskEntry.getKey(), subtaskEntry.getValue());
            }
            String jsonHistory = client.load("HISTORY");
            final List<Integer> historyId = gson.fromJson(jsonHistory, new TypeToken<List<Integer>>() {
            }.getType());
            for (int ID : historyId) {
                List<Task> allTasks = getAllTasks();
                List<Subtask> allSubtasks = getAllSubtasks();
                List<Epic> allEpics = getAllEpics();
                for (Task task : allTasks) {
                    if (ID == task.getTaskId()) {
                        history().add(task);
                    }
                }
                for (Subtask subtask : allSubtasks) {
                    if (ID == subtask.getTaskId()) {
                        history().add(subtask);
                    }
                }
                for (Epic epic : allEpics) {
                    if (ID == epic.getTaskId()) {
                        history().add(epic);
                    }
                }
            }
        } catch (InterruptedException | IOException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.");
        }
    }

        @Override
        public void save() {
            try {
                String jsonTask = gson.toJson(tasks);
                client.put("TASK", jsonTask);
                String jsonSubtask = gson.toJson(subtasks);
                client.put("SUBTASK", jsonSubtask);
                String jsonEpic = gson.toJson(epics);
                client.put("EPIC", jsonEpic);
                List<Task> history = history();
                String jsonHistory = gson.toJson(history.stream().map(Task::getTaskId).collect(Collectors.toList()));
                client.put("HISTORY", jsonHistory);
            } catch (InterruptedException | IOException e) {
                System.out.println("Во время выполнения запроса возникла ошибка.");
            }
        }



}



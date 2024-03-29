package httpserver.handlers;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class TaskHandler implements HttpHandler {

    private final TaskManager taskManager;
    private  final Gson gson;

    public TaskHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String str = exchange.getRequestMethod();
        if (str.equals("GET")) {
            getRequestTask(exchange);
        } else if (str.equals("POST")) {
            postRequestTask(exchange);
        } else if (str.equals("DELETE")) {
            deleteRequestTask(exchange);
        }
    }

    public void deleteRequestTask(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String path = exchange.getRequestURI().getPath();
        if (path.equals("/tasks/task/") && uri.getQuery() == null) {
            taskManager.deleteAllTasks();
            String response = "All Tasks deleted";
            exchange.sendResponseHeaders(200,0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } else if (uri.getQuery() != null) {
            taskManager.deleteTask(getIdfromUri(uri));
            String response = "Task with ID " + getIdfromUri(uri) + " deleted";
            exchange.sendResponseHeaders(200,0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    public void postRequestTask(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        Task task = gson.fromJson(body,Task.class);
        if (task.getTaskId() != 0) {
            taskManager.updateTask(task);
            exchange.sendResponseHeaders(200,0);
            String response = "Task updated!";
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } else {
            taskManager.addTask(task);
            exchange.sendResponseHeaders(200,0);
            String response = "Task added!";
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    public void getRequestTask(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String path = exchange.getRequestURI().getPath();
        if (path.equals("/tasks/task/") && uri.getQuery() == null) {
            String response = gson.toJson(taskManager.getAllTasks());
            exchange.sendResponseHeaders(200,0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } else if (uri.getQuery() != null) {
            String response = gson.toJson(taskManager.getTask(getIdfromUri(uri)));
            if (response.equals(null)) {
                exchange.sendResponseHeaders(400,0);
            }
            exchange.sendResponseHeaders(200,0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }

    }

    public Integer getIdfromUri(URI uri) {
        String str = uri.getQuery();
        return Integer.parseInt(str.split("=")[1]);
    }
}



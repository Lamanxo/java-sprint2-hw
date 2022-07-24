package httpserver.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;
import tasks.Subtask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class SubtaskHandler implements HttpHandler {
    private final TaskManager taskManager;
    private  final Gson gson;

    public SubtaskHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String str = exchange.getRequestMethod();
        if (str.equals("GET")) {
            getRequestSubtask(exchange);
        } else if (str.equals("POST")) {
            postRequestSubtask(exchange);
        } else if (str.equals("DELETE")) {
            deleteRequestSubtask(exchange);
        }
    }

    public void deleteRequestSubtask(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String path = exchange.getRequestURI().getPath();
        if (path.equals("/tasks/subtask/") && uri.getQuery() == null) {
            taskManager.deleteAllSubtasks();
            String response = "All Subs deleted";
            exchange.sendResponseHeaders(200,0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } else if (uri.getQuery() != null) {
            taskManager.deleteSubtask(getIdfromUri(uri));
            String response = "Task with ID " + getIdfromUri(uri) + " deleted";
            exchange.sendResponseHeaders(200,0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    public void postRequestSubtask(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        Subtask subtask = gson.fromJson(body,Subtask.class);
        if (subtask.getTaskId() != 0) {
            taskManager.updateSubtask(subtask);
            exchange.sendResponseHeaders(200,0);
            String response = "Subtask updated";
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } else {
            taskManager.addSubtask(subtask);
            exchange.sendResponseHeaders(200,0);
            String response = "Subtask added";
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    public void getRequestSubtask(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String path = exchange.getRequestURI().getPath();
        if (path.equals("/tasks/subtask/") && uri.getQuery() == null) {
            String response = gson.toJson(taskManager.getAllSubtasks());
            exchange.sendResponseHeaders(200,0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } else if (uri.getQuery() != null) {
            String response = gson.toJson(taskManager.getSubtask(getIdfromUri(uri)));
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

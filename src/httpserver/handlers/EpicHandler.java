package httpserver.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;
import tasks.Epic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class EpicHandler implements HttpHandler {
    private final TaskManager taskManager;
    private  final Gson gson;

    public EpicHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String str = exchange.getRequestMethod();
        if (str.equals("GET")) {
            getRequestEpic(exchange);
        } else if (str.equals("POST")) {
            postRequestEpic(exchange);
        } else if (str.equals("DELETE")) {
            deleteRequestEpic(exchange);
        }
    }

    public void deleteRequestEpic(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String path = exchange.getRequestURI().getPath();
        if (path.equals("/tasks/epic/") && uri.getQuery() == null) {
            taskManager.deleteAllEpics();
            String response = "All Epics deleted";
            exchange.sendResponseHeaders(200,0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } else if (uri.getQuery() != null) {
            taskManager.deleteEpic(getIdfromUri(uri));
            String response = "Task with ID " + getIdfromUri(uri) + " deleted";
            exchange.sendResponseHeaders(200,0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    public void postRequestEpic(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        Epic epic = gson.fromJson(body,Epic.class);
        if (epic.getTaskId() != 0) {
            taskManager.updateEpic(epic);
            exchange.sendResponseHeaders(200,0);
            String response = "Epic updated";
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } else {
            taskManager.addEpic(epic);
            exchange.sendResponseHeaders(200,0);
            String response = "Epic added";
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    public void getRequestEpic(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String path = exchange.getRequestURI().getPath();
        if (path.equals("/tasks/epic/") && uri.getQuery() == null) {
            String response = gson.toJson(taskManager.getAllEpics());
            exchange.sendResponseHeaders(200,0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } else if (uri.getQuery() != null) {
            String response = gson.toJson(taskManager.getEpic(getIdfromUri(uri)));
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

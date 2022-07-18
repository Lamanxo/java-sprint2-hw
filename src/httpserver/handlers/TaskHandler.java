package httpserver.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;

import java.io.IOException;
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
        String response = "";
        String str = exchange.getRequestMethod();
        if (str.equals("GET")) {
            URI uri = exchange.getRequestURI();

            response = getRequest(exchange);
        } else if (str.equals("POST")) {
            postTaskRequest(exchange);
        } else if (str.equals("DELETE")) {
            deleteTaskRquest(exchange);
        }
        try (OutputStream os = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(200,0);
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }

    }

    public void

}

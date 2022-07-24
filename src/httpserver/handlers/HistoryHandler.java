package httpserver.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class HistoryHandler implements HttpHandler{

        private final TaskManager taskManager;
        private  final Gson gson;

        public HistoryHandler(TaskManager taskManager, Gson gson) {
            this.taskManager = taskManager;
            this.gson = gson;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String str = exchange.getRequestMethod();
            if (str.equals("GET")) {
                getRequestHistory(exchange);
            }
        }

    public void getRequestHistory(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String path = exchange.getRequestURI().getPath();
        if (path.equals("/tasks/history/") && uri.getQuery() == null) {
            String response = gson.toJson(taskManager.history());
            exchange.sendResponseHeaders(200,0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }
    }


}

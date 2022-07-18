package httpserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import manager.TaskManager;

import java.io.IOException;
import java.time.Duration;

public class HttpTaskServer {

    private final int PORT = 8080;
    private HttpServer httpServer;
    private Gson gson;
    private TaskManager taskManager;


}


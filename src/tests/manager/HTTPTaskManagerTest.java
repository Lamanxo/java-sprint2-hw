
package manager;

import com.google.gson.Gson;
import httpserver.HttpTaskServer;
import httpserver.KVServer;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPTaskManagerTest {
    HttpClient client = HttpClient.newHttpClient();
    private Gson gson;

    KVServer kvServer;
    HttpTaskServer httpTaskServer;

    void start() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();
    }

    @Test
    void saveEndpointsByHttpAndCheckRequests() throws IOException, InterruptedException {
        start();
        HTTPTaskManager manager = (HTTPTaskManager) Managers.getDefault();
        Task task = new Task("task1", "taskDesc1", Status.NEW,
                LocalDateTime.of(2022, 6, 9, 3, 0),Duration.ofHours(1));
        Epic epic1 = new Epic( "epic1", "epicDesc1");
        Subtask subtask1 = new Subtask("subtask1", "subtaskDesc1", Status.NEW,
                1, LocalDateTime.of(2022, 6, 9, 5, 0), Duration.ofHours(1));
        Subtask subtask2 = new Subtask("subtask2", "subtaskDesc2", Status.NEW,
                1, LocalDateTime.of(2022, 6, 9, 9, 0),Duration.ofHours(1));


        gson = new Gson();
        URI epicUri = URI.create("http://localhost:8080/tasks/epic/");
        URI subtasksUri = URI.create("http://localhost:8080/tasks/subtask/");
        URI taskUri = URI.create("http://localhost:8080/tasks/task/");
        manager.addTask(task);
        manager.addEpic(epic1);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        String taskJson = gson.toJson(task);//id=1
        String epicJson1 = gson.toJson(epic1);//id=2
        String subtaskJson1 = gson.toJson(subtask1);//id=3
        String subtaskJson2 = gson.toJson(subtask2);//id=4

        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(taskJson);
        HttpRequest request = HttpRequest.newBuilder().uri(taskUri).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        final HttpRequest.BodyPublisher bodyEpic1 = HttpRequest.BodyPublishers.ofString(epicJson1);
        HttpRequest requestEpic1 = HttpRequest.newBuilder().uri(epicUri).POST(bodyEpic1).build();
        HttpResponse<String> responseEpic1 = client.send(requestEpic1, HttpResponse.BodyHandlers.ofString());

        final HttpRequest.BodyPublisher bodySubtask1 = HttpRequest.BodyPublishers.ofString(subtaskJson1);
        HttpRequest requestSubtask1 = HttpRequest.newBuilder().uri(subtasksUri).POST(bodySubtask1).build();
        HttpResponse<String> responseSubtask1 = client.send(requestSubtask1, HttpResponse.BodyHandlers.ofString());

        final HttpRequest.BodyPublisher bodySubtask2 = HttpRequest.BodyPublishers.ofString(subtaskJson2);
        HttpRequest requestSubtask2 = HttpRequest.newBuilder().uri(subtasksUri).POST(bodySubtask2).build();
        HttpResponse<String> responseSubtask2 = client.send(requestSubtask2, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(200, responseEpic1.statusCode());
        assertEquals(200, responseSubtask1.statusCode());
        assertEquals(200, responseSubtask2.statusCode());

        //Проверка GetEpic
        Epic localEpic = manager.getEpic(2);
        URI uri = URI.create(epicUri + "?id=2");
        HttpRequest request2 = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response2 = client.send(request2, handler);
        Epic serverEpic = gson.fromJson(response2.body(), Epic.class);
        assertEquals(localEpic, serverEpic);

        //Проверка GetSubtask
        Subtask localSubtask = manager.getSubtask(4);
        URI uri1 = URI.create(subtasksUri + "?id=4");
        HttpRequest request3 = HttpRequest.newBuilder()
                .GET()
                .uri(uri1)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler1 = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response3 = client.send(request3, handler1);
        Subtask serverSubtask = gson.fromJson(response3.body(), Subtask.class);
        assertEquals(localSubtask, serverSubtask);

        //Проверка GetTask
        Subtask localTask = manager.getSubtask(1);
        URI uri2 = URI.create(taskUri + "?id=1");
        HttpRequest request4 = HttpRequest.newBuilder()
                .GET()
                .uri(uri2)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler2 = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response4 = client.send(request4, handler2);
        Task serverTask = gson.fromJson(response4.body(), Task.class);
        assertEquals(localTask, serverTask);



    }
}

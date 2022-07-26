
import com.google.gson.Gson;
import httpserver.HttpTaskServer;
import httpserver.KVServer;
import tasks.Status;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;




public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Пришло время практики!");
        HttpClient client = HttpClient.newHttpClient();
        new KVServer().start();
        HttpTaskServer server = new HttpTaskServer();
        server.start();

        //для проверки на всякий

        Task task = new Task("task1", "taskDesc1", Status.NEW,
                LocalDateTime.of(2022, 6, 9, 3, 0), Duration.ofHours(1));
        Gson gson = new Gson();
        URI taskUri = URI.create("http://localhost:8080/tasks/task/");

        String taskJson = gson.toJson(task);//id=1

        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(taskJson);
        HttpRequest request = HttpRequest.newBuilder().uri(taskUri).POST(body).build();
        HttpResponse<String> responseTask = client.send(request, HttpResponse.BodyHandlers.ofString());

        //Проверка GetTask

        URI uri2 = URI.create(taskUri + "?id=1");
        HttpRequest request4 = HttpRequest.newBuilder()
                .GET()
                .uri(uri2)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler2 = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response4 = client.send(request4, handler2);
        Task serverTask = gson.fromJson(response4.body(), Task.class);

        System.out.println("Task from server \n" + serverTask);
        System.out.println("Task as it created \n" + task); //ID генерируется при попадании в менеджер

    }
}

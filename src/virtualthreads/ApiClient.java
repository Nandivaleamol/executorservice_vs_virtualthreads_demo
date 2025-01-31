package virtualthreads;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

/**
 * Suppose you are building a service that processes a large number of I/O-bound tasks,
 * such as making HTTP requests to external APIs.
 */
public class ApiClient {
    public static void main(String[] args) {
        var startMillis = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 100; i++) {
                executor.submit(() -> {
                    System.out.println("Making API request on thread: " + Thread.currentThread());
                    // Simulate an HTTP request
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                            .build();
                    try {
                        System.out.println("Sending request to api");
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        System.out.println("Response: " + response.body());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        System.out.println("Total execution time: " + (System.currentTimeMillis() - startMillis));
    }
}

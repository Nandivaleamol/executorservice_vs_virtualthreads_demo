package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Suppose you are building a web server that handles multiple client requests concurrently.
 * Each request is a task that can be executed independently.
 */
public class WebServer {
    private static final int THREAD_POOL_SIZE = 100;
    private static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
//    private static final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();  // Total execution time: 44

    public static void main(String[] args) {
        var startMillis = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                System.out.println("Handling request on thread: " + Thread.currentThread().getName());

                // Simulate request processing
                try {
                    Thread.sleep(1000);
                    System.out.println("Request processed successfully.");
                } catch (InterruptedException e) {
                    var interrupted = Thread.interrupted();
                    System.out.println(interrupted);
                }
            });
        }

        // Shutdown after all tasks complete
        executor.shutdown();
        System.out.println("Total execution time: " + (System.currentTimeMillis() - startMillis));
    }
}

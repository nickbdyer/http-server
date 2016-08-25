package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

public class HttpServer {

    private final ExecutorService executor;
    private final ServerSocket serverSocket;
    private final String directoryPath;

    public HttpServer(ExecutorService executor, ServerSocket serverSocket, String directoryPath) {
        this.executor = executor;
        this.serverSocket = serverSocket;
        this.directoryPath = directoryPath;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void listen() {
        executor.execute(() -> {
            try {
                serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

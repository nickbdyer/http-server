package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class HttpServer {

    private final ExecutorService executorService;
    private final ServerSocket serverSocket;
    private final Router router;

    public HttpServer(ExecutorService executorService, ServerSocket serverSocket, Router router) {
        this.executorService = executorService;
        this.serverSocket = serverSocket;
        this.router = router;
    }

    public void listen() {
        try {
            Socket connection;
            while ((connection = serverSocket.accept()) != null) {
                Socket finalConnection = connection;
                executorService.execute(() -> {
                    new SocketHandler(finalConnection, router).processRequestAndRespond();
                });
            }
        } catch (IOException e) {
            if ("Socket closed".equals(e.getMessage())) {
                System.out.println(("Server shutdown..."));
            } else {
                throw new UncheckedIOException(e);
            }
        }
    }
}

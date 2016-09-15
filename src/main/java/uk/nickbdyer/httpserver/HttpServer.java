package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.middleware.MiddlewareStack;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class HttpServer {

    private final ExecutorService executorService;
    private final ServerSocket serverSocket;
    private final MiddlewareStack middlewareStack;

    public HttpServer(ExecutorService executorService, ServerSocket serverSocket, MiddlewareStack middlewareStack) {
        this.executorService = executorService;
        this.serverSocket = serverSocket;
        this.middlewareStack = middlewareStack;
    }

    public void listen() {
        try {
            Socket connection;
            while ((connection = serverSocket.accept()) != null) {
                Socket finalConnection = connection;
                executorService.execute(() -> {
                    new SocketHandler(finalConnection, middlewareStack).processRequestAndRespond();
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

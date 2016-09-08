package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.middleware.Logger;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class HttpServer {

    private final ExecutorService executorService;
    private final ServerSocket serverSocket;
    private final Router router;
    private final Logger logger;

    public HttpServer(ExecutorService executorService, ServerSocket serverSocket, Router router, Logger logger) {
        this.executorService = executorService;
        this.serverSocket = serverSocket;
        this.router = router;
        this.logger = logger;
    }

    public void listen() {
        try {
            Socket connection;
            while ((connection = serverSocket.accept()) != null) {
                Socket finalConnection = connection;
                executorService.execute(() -> {
                    new SocketHandler(finalConnection, logger, router).processRequestAndRespond();
                });
            }
        } catch (IOException e) {
            if ("Socket closed".equals(e.getMessage())) {
                logger.log("Server shutdown...");
            } else {
                throw new UncheckedIOException(e);
            }
        }
    }
}

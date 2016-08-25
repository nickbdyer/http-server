package uk.nickbdyer.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
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
//                Plenty of things to refactor here

                Socket connection = serverSocket.accept();
                OutputStream response = connection.getOutputStream();
                String request = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                if ("GET /".equals(request)) {
                    response.write("HTTP/1.1 200 OK".getBytes());
                } else {
                    response.write("HTTP/1.1 404 Not Found".getBytes());
                }
                response.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

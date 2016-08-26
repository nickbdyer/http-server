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
//                AwaitConnections
                Socket connection = serverSocket.accept();
//                AwaitRequest
                String request = new SocketHandler(connection).getRequest();
//                HandleRequest
//                Build response
                OutputStream response = connection.getOutputStream();
                if ("GET / HTTP/1.1".equals(request)) {
                    response.write("HTTP/1.1 200 OK\n".getBytes());
                } else if ("POST /form HTTP/1.1".equals(request)) {
                    response.write("HTTP/1.1 200 OK\n".getBytes());
                } else {
                    response.write("HTTP/1.1 404 Not Found\n".getBytes());
                }
//                SendResponse
                response.flush();
//                TearDown
                response.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

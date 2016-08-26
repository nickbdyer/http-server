package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private final ServerSocket serverSocket;
    private final String directoryPath;

    public HttpServer(ServerSocket serverSocket, String directoryPath) {
        this.serverSocket = serverSocket;
        this.directoryPath = directoryPath;
    }

    public void listen() {
        try {
            Socket connection = serverSocket.accept();
            while (connection != null) {
                String request = new SocketHandler(connection).getRequest();

//              Build response
                OutputStream response = connection.getOutputStream();
                if ("GET / HTTP/1.1".equals(request)) {
                    response.write("HTTP/1.1 200 OK\n".getBytes());
                } else if ("POST /form HTTP/1.1".equals(request)) {
                    response.write("HTTP/1.1 200 OK\n".getBytes());
                } else {
                    response.write("HTTP/1.1 404 Not Found\n".getBytes());
                }
                response.flush();
                response.close();
                connection = serverSocket.accept();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

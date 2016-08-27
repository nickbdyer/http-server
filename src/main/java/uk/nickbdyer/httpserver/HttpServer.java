package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpServer {

    private final ConnectionHandler connectionHandler;
    private final String directoryPath;

    public HttpServer(ConnectionHandler connectionHandler, String directoryPath) {
        this.connectionHandler = connectionHandler;
        this.directoryPath = directoryPath;
    }

    public void listen() {
        try {
            Socket connection = connectionHandler.getSocket();
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
                connection = connectionHandler.getSocket();
            }
        } catch (IOException e) {
            if ("Socket closed".equals(e.getMessage())) {
                System.out.println("Server shutdown...");
            } else {
                e.printStackTrace();
            }
        }
    }
}

package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpServer {

    private final ConnectionHandler connectionHandler;
    private final RequestParser parser;
    private final String directoryPath;

    public HttpServer(ConnectionHandler connectionHandler, RequestParser parser, String directoryPath) {
        this.connectionHandler = connectionHandler;
        this.parser = parser;
        this.directoryPath = directoryPath;
    }

    public void listen() {
        try {
            Socket connection = connectionHandler.getSocket();
            while (connection != null) {
                String requestString = new SocketHandler(connection).getRequest();
                Request request = parser.parse(requestString);

//              Build response
                OutputStream response = connection.getOutputStream();
                if (parser.isValid(request)) {
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

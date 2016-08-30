package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpServer {

    private final ConnectionHandler connectionHandler;
    private final RequestParser parser;

    public HttpServer(ConnectionHandler connectionHandler, RequestParser parser) {
        this.connectionHandler = connectionHandler;
        this.parser = parser;
    }

    public void listen() {
        try {
            Socket connection = connectionHandler.getSocket();
            while (connection != null) {
                String requestString = new SocketHandler(connection).getRequest();
                Request request = parser.parse(requestString);

//              Build response NEEDS REFACTORING, maybe after Interface for Response.
                OutputStream response = connection.getOutputStream();

                String statusLine = parser.getResponse(request).getStatusLine();
                String responseHeader = parser.getResponse(request).getResponseHeader();

                response.write(statusLine.getBytes());
                response.write(responseHeader.getBytes());

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

package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.net.Socket;

public class HttpServer {

    private final ConnectionHandler connectionHandler;
    private final ResponseBuilder builder;

    public HttpServer(ConnectionHandler connectionHandler, ResponseBuilder builder) {
        this.connectionHandler = connectionHandler;
        this.builder = builder;
    }

    public void listen() {
        try {
            Socket connection = connectionHandler.getSocket();
            while (connection != null) {
                SocketHandler socketHandler = new SocketHandler(connection);
                RequestParser parser = new RequestParser();

                String requestString = socketHandler.getRequest();
                Request request = parser.parse(requestString);
                String responseString = builder.getResponse(request).getResponse();
                socketHandler.sendResponse(responseString);

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

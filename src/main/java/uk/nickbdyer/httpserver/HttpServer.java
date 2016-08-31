package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.io.OutputStream;
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
                String requestString = new SocketHandler(connection).getRequest();
                RequestParser parser = new RequestParser();
                Request request = parser.parse(requestString);

//              Build response NEEDS REFACTORING, maybe after Interface for ConcreteResponse.
                OutputStream response = connection.getOutputStream();

                String responseString = builder.getResponse(request).getResponse();

                response.write(responseString.getBytes());

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

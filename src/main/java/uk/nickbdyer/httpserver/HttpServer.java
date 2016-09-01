package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private final ServerSocket serverSocket;
    private final Router router;

    public HttpServer(ServerSocket serverSocket, Router router) {
        this.serverSocket = serverSocket;
        this.router = router;
    }

    public void listen() {
        try {
            Socket connection = serverSocket.accept();
            while (connection != null) {
                // Pass socket to requestParser
                RequestParser parser = new RequestParser(connection);
                // requestParser.parse return RequestObject
                String requestString = parser.getRequest();
                Request request = parser.parse();
                // Router take RequestObject
                String responseString = router.getResponse(request).getResponse();
                // Route request to appropriate response builder
                // Response to dispatcher with socket
                parser.sendResponse(responseString);

                connection = serverSocket.accept();
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

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
                SocketHandler socketHandler = new SocketHandler(connection);
                RequestParser parser = new RequestParser();

                String requestString = socketHandler.getRequest();
                Request request = parser.parse(requestString);
                String responseString = router.getResponse(request).getResponse();
                socketHandler.sendResponse(responseString);

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

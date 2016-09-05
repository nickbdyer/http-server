package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.requests.RequestParser;
import uk.nickbdyer.httpserver.responses.Response;
import uk.nickbdyer.httpserver.responses.ResponseDispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

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
                RequestParser parser = new RequestParser(connection);

                Request request = parser.parse();

                Response response = router.route(request);
                int code = response.getStatusCode();
                Map<String, String> headers = response.getHeaders();
                byte[] body = response.getBody();

                new ResponseDispatcher(connection).sendResponse(code, headers, body);

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

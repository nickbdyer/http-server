package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.requests.RequestParser;
import uk.nickbdyer.httpserver.responses.Response;
import uk.nickbdyer.httpserver.responses.ResponseDispatcher;

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
                RequestParser parser = new RequestParser(connection);

                Request request = parser.parse();

                Response response = router.route(request);
                int code = response.getStatusCode();
                String statusAndHeaders = response.getStatusLineAndHeader();
                byte[] body = response.getResponseBody();

                new ResponseDispatcher(connection).sendResponse(code, statusAndHeaders, body);

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

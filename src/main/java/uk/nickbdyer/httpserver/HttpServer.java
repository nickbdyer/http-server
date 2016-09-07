package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.middleware.Logger;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.requests.RequestParser;
import uk.nickbdyer.httpserver.responses.Response;
import uk.nickbdyer.httpserver.responses.ResponseFormatter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private final ServerSocket serverSocket;
    private final Router router;
    private final Logger logger;

    public HttpServer(ServerSocket serverSocket, Router router, Logger logger) {
        this.serverSocket = serverSocket;
        this.router = router;
        this.logger = logger;
    }

    public void listen() {
        try {
            Socket connection = serverSocket.accept();
            while (connection != null) {
                RequestParser parser = new RequestParser(connection, logger);

                Request request = parser.parse();

                Response response = router.route(request);


                new ResponseFormatter(connection, response).sendResponse();

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

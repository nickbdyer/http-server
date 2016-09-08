package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.middleware.Logger;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.requests.RequestParser;
import uk.nickbdyer.httpserver.responses.Response;
import uk.nickbdyer.httpserver.responses.ResponseFormatter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Socket;

public class SocketHandler {
    private final Socket socket;
    private final Logger logger;
    private final Router router;

    public SocketHandler(Socket socket, Logger logger, Router router) {
        this.socket = socket;
        this.logger = logger;
        this.router = router;
    }

    public void processRequestAndRespond() {
        try {
            RequestParser parser = new RequestParser(socket, logger);
            Request request = parser.parse();
            Response response = router.route(request);
            new ResponseFormatter(socket, response).sendResponse();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }
}

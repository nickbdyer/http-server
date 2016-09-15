package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.middleware.MiddlewareStack;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.requests.RequestParser;
import uk.nickbdyer.httpserver.responses.Response;
import uk.nickbdyer.httpserver.responses.ResponseFormatter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Socket;

public class SocketHandler {
    private final Socket socket;
    private final MiddlewareStack middlewareStack;

    public SocketHandler(Socket socket, MiddlewareStack middlewareStack) {
        this.socket = socket;
        this.middlewareStack = middlewareStack;
    }

    public void processRequestAndRespond() {
        try {
            RequestParser parser = new RequestParser(socket);
            Request request = parser.parse();
            Response response = middlewareStack.call(request);
            new ResponseFormatter(socket, response).sendResponse();
        } catch (Exception e) {
            returnA500Error();
        }
    }

    private void returnA500Error() {
        try {
            new ResponseFormatter(socket, Response.ServerError()).sendResponse();
        } catch (IOException e1) {
            throw new UncheckedIOException(e1);
        }
    }
}

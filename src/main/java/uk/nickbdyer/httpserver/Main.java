package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import static uk.nickbdyer.httpserver.Method.*;

public class Main {

    public static void main(String[] args) throws IOException {
        List<Request> validRequests = new ArrayList<>();

        validRequests.add(new Request(GET, "/"));
        validRequests.add(new Request(GET, "/redirect"));
        validRequests.add(new Request(POST, "/form"));
        validRequests.add(new Request(PUT, "/form"));

        RequestParser parser = new RequestParser(validRequests);
        new HttpServer(new ConnectionHandler(new ServerSocket(Arguments.getPort(args))), parser, Arguments.getDirectoryPath(args)).listen();
    }

}

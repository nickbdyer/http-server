package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import static uk.nickbdyer.httpserver.Method.*;
import static uk.nickbdyer.httpserver.Response.*;

public class Main {

    public static void main(String[] args) throws IOException {
        RequestParser parser = new RequestParser();
        parser.add(new Request(GET, "/").thatRespondsWith(OK()));
        parser.add(new Request(GET, "/redirect").thatRespondsWith(Redirect("http://localhost:5000/")));
        parser.add(new Request(POST, "/form").thatRespondsWith(OK()));
        parser.add(new Request(PUT, "/form").thatRespondsWith(OK()));

        new HttpServer(new ConnectionHandler(new ServerSocket(Arguments.getPort(args))), parser, Arguments.getDirectoryPath(args)).listen();
    }

}

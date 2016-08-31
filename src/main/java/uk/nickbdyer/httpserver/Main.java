package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.Responses.OK;
import uk.nickbdyer.httpserver.Responses.Redirect;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import static uk.nickbdyer.httpserver.Method.*;

public class Main {

    public static void main(String[] args) throws IOException {
        ResponseBuilder builder = new ResponseBuilder();
        builder.add(new Request(GET, "/").thatRespondsWith(new OK()));
        builder.add(new Request(GET, "/redirect").thatRespondsWith(new Redirect("http://localhost:5000/")));
        builder.add(new Request(GET, "/form").thatRespondsWith(new OK()));
        builder.add(new Request(POST, "/form").thatRespondsWith(new OK()));
        builder.add(new Request(PUT, "/form").thatRespondsWith(new OK()));

        File folder = new File(Arguments.getDirectoryPath(args));
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            builder.add(new Request(GET, ("/".concat(files[i].getName()))).thatRespondsWith(new OK()));
        }

        new HttpServer(new ConnectionHandler(new ServerSocket(Arguments.getPort(args))), builder).listen();
    }

}

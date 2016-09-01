package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.Responses.OK;
import uk.nickbdyer.httpserver.Responses.Redirect;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import static uk.nickbdyer.httpserver.Method.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Arguments arguments = new Arguments(args);
        Router router = new Router();
        router.add(new Route(GET, "/").thatRespondsWith(new OK()));
        router.add(new Route(GET, "/redirect").thatRespondsWith(new Redirect("http://localhost:5000/")));
        router.add(new Route(GET, "/form").thatRespondsWith(new OK()));
        router.add(new Route(POST, "/form").thatRespondsWith(new OK()));
        router.add(new Route(PUT, "/form").thatRespondsWith(new OK()));

        File folder = new File(arguments.getDirectoryPath());
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            router.add(new Route(GET, ("/".concat(files[i].getName()))).thatRespondsWith(new OK()));
        }

        new HttpServer(new ServerSocket(arguments.getPort()), router).listen();
    }

}

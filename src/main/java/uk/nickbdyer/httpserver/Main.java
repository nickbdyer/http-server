package uk.nickbdyer.httpserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import static uk.nickbdyer.httpserver.Method.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Arguments arguments = new Arguments(args);
        Router router = new Router();
        router.add(new Route(GET, "/").thatRespondsWith(new Response("HTTP/1.1 200 OK", "", "")));
        router.add(new Route(GET, "/redirect").thatRespondsWith(new Response("HTTP/1.1 302 Found", router.createResponseHeader("http://localhost:5000/"), "")));
        router.add(new Route(GET, "/form").thatRespondsWith(new Response("HTTP/1.1 200 OK", "", "")));
        router.add(new Route(POST, "/form").thatRespondsWith(new Response("HTTP/1.1 200 OK", "", "")));
        router.add(new Route(PUT, "/form").thatRespondsWith(new Response("HTTP/1.1 200 OK", "", "")));

        File folder = new File(arguments.getDirectoryPath());
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            router.add(new Route(GET, ("/".concat(files[i].getName()))).thatRespondsWith(new Response("HTTP/1.1 200 OK", "", "")));
        }

        new HttpServer(new ServerSocket(arguments.getPort()), router).listen();
    }

}

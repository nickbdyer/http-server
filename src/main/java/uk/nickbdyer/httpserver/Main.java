package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.controllers.FormController;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.function.BiFunction;

import static uk.nickbdyer.httpserver.Method.*;

public class Main {

    public static void main(String[] args) throws IOException {
        FormData form = new FormData();
        Arguments arguments = new Arguments(args);
        Router router = new Router();
        router.add(new Route(GET, "/"));
        router.add(new Route(GET, "/redirect").that(redirects));
        router.add(new Route(GET, "/form").from(form));
        router.add(new Route(POST, "/form").that(writesData).to(form));
        router.add(new Route(PUT, "/form").that(writesData).to(form));
        router.add(new Route(DELETE, "/form").that(deletesData).from(form));
        router.addController("/form", new FormController(form));

        File folder = new File(arguments.getDirectoryPath());
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            router.add(new Route(GET, ("/".concat(files[i].getName()))));
        }

        new HttpServer(new ServerSocket(arguments.getPort()), router).listen();
    }

    private static BiFunction<Route, Request, Response> writesData = (route, request) -> {
        if (request.getBody() != null) {
            route.setBody(request.getBody());
        }
        return new Response("HTTP/1.1 200 OK", "", route.getBody().getData());
    };

    private static BiFunction<Route, Request, Response> deletesData = (route, request) -> {
        route.setBody("");
        return new Response("HTTP/1.1 200 OK", "", route.getBody().getData());
    };

    private static BiFunction<Route, Request, Response> redirects = (route, request) ->
            new Response("HTTP/1.1 302 Found", new Router().createResponseHeader("http://localhost:5000/"), "");
}

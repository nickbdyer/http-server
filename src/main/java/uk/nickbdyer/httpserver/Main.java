package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.controllers.FormController;
import uk.nickbdyer.httpserver.controllers.RedirectController;
import uk.nickbdyer.httpserver.controllers.RootController;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import static uk.nickbdyer.httpserver.Method.GET;

public class Main {

    public static void main(String[] args) throws IOException {
        FormData form = new FormData();
        Arguments arguments = new Arguments(args);

        Router router = new Router();
        router.addController("/", new RootController());
        router.addController("/redirect", new RedirectController());
        router.addController("/form", new FormController(form));

        File folder = new File(arguments.getDirectoryPath());
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            router.add(new Route(GET, ("/".concat(files[i].getName()))));
        }

        new HttpServer(new ServerSocket(arguments.getPort()), router).listen();
    }

}

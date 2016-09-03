package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.controllers.FormController;
import uk.nickbdyer.httpserver.controllers.FormData;
import uk.nickbdyer.httpserver.controllers.RedirectController;
import uk.nickbdyer.httpserver.controllers.RootController;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        Arguments arguments = new Arguments(args);
        File publicFolder = new File(arguments.getDirectoryPath());

        FormData form = new FormData();
        Router router = new Router(publicFolder);
        router.addController("/", new RootController());
        router.addController("/redirect", new RedirectController());
        router.addController("/form", new FormController(form));

        new HttpServer(new ServerSocket(arguments.getPort()), router).listen();
    }

}

package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.controllers.*;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        Arguments arguments = new Arguments(args);
        File publicFolder = new File(arguments.getDirectoryPath());

        FormData form = new FormData(null);
        Router router = new Router(publicFolder);
        router.addController("/", new RootController(publicFolder));
        router.addController("/redirect", new RedirectController());
        router.addController("/form", new FormController(form));
        router.addController("/parameters", new ParameterController());
        router.addController("/method_options", new MethodOptionsController());
        router.addController("/method_options2", new MethodOptions2Controller());
        router.addController("/coffee", new CoffeeController());
        router.addController("/tea", new TeaController());

        new HttpServer(new ServerSocket(arguments.getPort()), router).listen();
    }

}

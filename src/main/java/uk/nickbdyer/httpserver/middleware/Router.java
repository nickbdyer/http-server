package uk.nickbdyer.httpserver.middleware;

import uk.nickbdyer.httpserver.controllers.Controller;
import uk.nickbdyer.httpserver.controllers.FileController;
import uk.nickbdyer.httpserver.filemanager.FileFinder;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.util.HashMap;
import java.util.Map;

import static uk.nickbdyer.httpserver.responses.Response.NotFound;

public class Router extends Middleware {

    private final FileFinder fileFinder;
    private Map<String, Controller> routeTable;

    public Router(FileFinder fileFinder) {
        this.fileFinder = fileFinder;
        routeTable = new HashMap<>();
    }

    public void addController(String path, Controller controller) {
        routeTable.put(path, controller);
    }

    @Override
    public Response call(Request request) {
        if(request.getMethod() == null) return NotFound(); //Catches preload requests, and parse failures.
        if (routeTable.containsKey(request.getPath())) {
            return routeTable.get(request.getPath()).execute(request);
        } else if (fileFinder.fileExists(request.getPath().substring(1))) {
            return new FileController(fileFinder.getFile(request.getPath().substring(1))).execute(request);
        }
        return next.call(request);
    }

}

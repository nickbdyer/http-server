package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.controllers.Controller;
import uk.nickbdyer.httpserver.controllers.FileController;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Router {

    private final File publicFolder;
    private Map<String, Controller> routeTable;

    public Router(File publicFolder) {
        this.publicFolder = publicFolder;
        routeTable = new HashMap<>();
    }

    public void addController(String path, Controller controller) {
        routeTable.put(path, controller);
    }

    public Response route(Request request) {
        if (fileExistsIn(publicFolder, request.getPath())) {
            routeTable.put(request.getPath(), new FileController(getFile(publicFolder, request.getPath())));
        }
        if (!routeTable.containsKey(request.getPath())) {
            return NotFound();
        } else {
            return routeTable.get(request.getPath()).execute(request);
        }
    }

    private boolean fileExistsIn(File publicFolder, String path) {
        File[] files = publicFolder.listFiles();
        if (files == null) return false;
        return Arrays.stream(files).anyMatch(file -> file.getName().equals(path.substring(1)));
    }

    private File getFile(File publicFolder, String path) {
        return Arrays.stream(publicFolder.listFiles())
                .filter(file -> Objects.equals(file.getName(), path.substring(1)))
                .collect(Collectors.toList()).get(0);
    }

    private Response NotFound() {
        return new Response("HTTP/1.1 404 Not Found", "", "");
    }


}

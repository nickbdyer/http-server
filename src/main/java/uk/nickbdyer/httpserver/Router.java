package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.controllers.Controller;
import uk.nickbdyer.httpserver.controllers.FileController;
import uk.nickbdyer.httpserver.requests.Method;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Router {

    private final File publicFolder;
    private Map<String, Controller> routeTable;

    public Router(File publicFolder) {
        this.publicFolder = publicFolder;
        routeTable = new HashMap<>();
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

    private File getFile(File publicFolder, String path) {
        return Arrays.stream(publicFolder.listFiles())
                .filter(file -> Objects.equals(file.getName(), path.substring(1)))
                .collect(Collectors.toList()).get(0);
    }

    private boolean fileExistsIn(File publicFolder, String path) {
        File[] files = publicFolder.listFiles();
        if (files == null) return false;
        return Arrays.stream(files).anyMatch(file -> file.getName().equals(path.substring(1)));
    }

    private Response NotFound() {
        return new Response("HTTP/1.1 404 Not Found", "", null);
    }

    public String createResponseHeader(List<Method> allowedMethods) {
        List<String> methods = allowedMethods.stream()
                .map(Enum::toString)
                .collect(Collectors.toList());
        return "Allow: " + String.join(", ", methods) + "\n";
    }

    public String createResponseHeader(String location) {
        return "Location: " + location + "\n";
    }

    public void addController(String path, Controller controller) {
        routeTable.put(path, controller);
    }

}

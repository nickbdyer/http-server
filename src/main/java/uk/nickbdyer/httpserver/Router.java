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

    private final File[] publicFiles;
    private Map<String, Controller> routeTable;

    public Router(File publicFolder) {
        this.publicFiles = publicFolder.listFiles();
        routeTable = new HashMap<>();
    }

    public void addController(String path, Controller controller) {
        routeTable.put(path, controller);
    }

    public Response route(Request request) {
        String requestPath = request.getPath();
        if (publicFileExists(requestPath)) {
            routeTable.put(requestPath, new FileController(getFile(requestPath)));
        }
        if (!routeTable.containsKey(requestPath)) {
            return Response.NotFound();
        } else {
            return routeTable.get(requestPath).execute(request);
        }
    }

    private boolean publicFileExists(String path) {
        return publicFiles != null && Arrays.stream(publicFiles)
                .anyMatch(file -> file.getName().equals(path.substring(1)));
    }

    private File getFile(String path) {
        return Arrays.stream(publicFiles)
                .filter(file -> Objects.equals(file.getName(), path.substring(1)))
                .collect(Collectors.toList()).get(0);
    }

}

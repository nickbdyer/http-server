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
        if (publicFileExists(request.getPath())) {
            routeTable.put(request.getPath(), new FileController(getFile(request.getPath())));
        }
        if (!routeTable.containsKey(request.getPath())) {
            return Response.NotFound();
        } else {
            return routeTable.get(request.getPath()).execute(request);
        }
    }

    private boolean publicFileExists(String path) {
        File[] files = publicFolder.listFiles();
        return files != null && Arrays.stream(files)
                .anyMatch(file -> file.getName().equals(path.substring(1)));
    }

    private File getFile(String path) {
        return Arrays.stream(publicFolder.listFiles())
                .filter(file -> Objects.equals(file.getName(), path.substring(1)))
                .collect(Collectors.toList()).get(0);
    }

}

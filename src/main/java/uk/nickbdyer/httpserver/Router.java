package uk.nickbdyer.httpserver;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static uk.nickbdyer.httpserver.Method.GET;
import static uk.nickbdyer.httpserver.Method.HEAD;

public class Router {

    private Map<String, List<Route>> definedRoutes;
    private Map<String, Controller> routeTable;

    public Router() {
        definedRoutes = new HashMap<>();
        routeTable = new HashMap<>();
    }

    public void add(Route route) {
        List<Route> allowedMethods;
        if (isExistingRoute(route.getPath())) {
            allowedMethods = definedRoutes.get(route.getPath());
            allowedMethods.add(route);
        } else {
            allowedMethods = new ArrayList<>(Arrays.asList(route));
            definedRoutes.put(route.getPath(), allowedMethods);
        }
        if (newRequestIsGET(route)) {
            addHeadToAllowedMethods(route, allowedMethods);
        }
    }

    public Response getResponse(Request request) {
        if (!isExistingRoute(request.getPath())) {
            return NotFound();
        } else if (hasDefinedRoute(request)) {
            return getDefinedResponse(request);
        } else {
            return MethodNotAllowed(request);
        }
    }

    public Response getControllerResponse(Request request) {
        if (!routeTable.containsKey(request.getPath())) {
            return NotFound();
        } else {
            return routeTable.get(request.getPath()).execute(request);
        }
    }

    private Response getDefinedResponse(Request request) {
        return definedRoutes.get(request.getPath()).stream()
                .filter(matchingMethods(request))
                .collect(Collectors.toList()).get(0).getResponse(request);
    }

    private Response NotFound() {
        return new Response("HTTP/1.1 404 Not Found", "", null);
    }

    private Response MethodNotAllowed(Request request) {
        String headers = createResponseHeader(definedRoutes.get(request.getPath()).stream()
                .map(Route::getMethod)
                .collect(Collectors.toList()));
        return new Response("HTTP/1.1 405 Method Not Allowed", headers, null);
    }

    private boolean hasDefinedRoute(Request request) {
        return definedRoutes.get(request.getPath()).stream()
                .anyMatch(matchingMethods(request));
    }

    private boolean isExistingRoute(String path) {
        return definedRoutes.containsKey(path);
    }

    private void addHeadToAllowedMethods(Route route, List<Route> allowedMethods) {
        allowedMethods.add(new Route(HEAD, route.getPath()));
    }

    private boolean newRequestIsGET(Route route) {
        return route.getMethod() == GET;
    }

    private static Predicate<Route> matchingMethods(Request request) {
        return route -> route.getMethod().equals(request.getMethod());
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

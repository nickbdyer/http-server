package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.Responses.MethodNotAllowed;
import uk.nickbdyer.httpserver.Responses.NotFound;
import uk.nickbdyer.httpserver.Responses.OK;
import uk.nickbdyer.httpserver.Responses.Response;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static uk.nickbdyer.httpserver.Method.GET;
import static uk.nickbdyer.httpserver.Method.HEAD;

public class Router {

    private Map<String, List<Route>> definedRoutes;

    public Router() {
        this.definedRoutes = new HashMap<>();
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
            return new NotFound();
        } else if (hasDefinedRoute(request)) {
            return definedRoutes.get(request.getPath()).stream()
                    .filter(matchingMethods(request))
                    .collect(Collectors.toList()).get(0).getResponse();
        } else {
            return new MethodNotAllowed(definedRoutes.get(request.getPath()).stream()
                    .map(Route::getMethod)
                    .collect(Collectors.toList()));
        }
    }

    private boolean hasDefinedRoute(Request request) {
        return definedRoutes.get(request.getPath()).stream()
                .anyMatch(matchingMethods(request));
    }

    private boolean isExistingRoute(String path) {
        return definedRoutes.containsKey(path);
    }

    private void addHeadToAllowedMethods(Route route, List<Route> allowedMethods) {
        allowedMethods.add(new Route(HEAD, route.getPath()).thatRespondsWith(new OK()));
    }

    private boolean newRequestIsGET(Route route) {
        return route.getMethod() == GET;
    }

    private static Predicate<Route> matchingMethods(Request request) {
        return route -> route.getMethod().equals(request.getMethod());
    }

}

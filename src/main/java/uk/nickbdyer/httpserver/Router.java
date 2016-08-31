package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.Responses.MethodNotAllowed;
import uk.nickbdyer.httpserver.Responses.Response;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static uk.nickbdyer.httpserver.Method.GET;
import static uk.nickbdyer.httpserver.Method.HEAD;

public class Router {

    private Map<String, List<Request>> definedRoutes;

    public Router() {
        this.definedRoutes = new HashMap<>();
    }

    public void add(Request request) {
        List<Request> allowedMethods;
        if (isExistingRoute(request)) {
            allowedMethods = definedRoutes.get(request.getRoute());
            allowedMethods.add(request);
        } else {
            allowedMethods = new ArrayList<>(Arrays.asList(request));
            definedRoutes.put(request.getRoute(), allowedMethods);
        }
        if (newRequestIsGET(request)) {
            addHeadToAllowedMethods(request, allowedMethods);
        }
    }

    public Response getResponse(Request request) {
        if (!isExistingRoute(request)) {
            return request.getDefinedResponse();
        } else if (hasDefinedRoute(request)) {
            return definedRoutes.get(request.getRoute()).stream()
                    .filter(matchingMethods(request))
                    .collect(Collectors.toList()).get(0).getDefinedResponse();
        } else {
            return new MethodNotAllowed(definedRoutes.get(request.getRoute()).stream()
                    .map(Request::getMethod)
                    .collect(Collectors.toList()));
        }
    }

    private boolean hasDefinedRoute(Request request) {
        return definedRoutes.get(request.getRoute()).stream()
                .anyMatch(matchingMethods(request));
    }

    private boolean isExistingRoute(Request request) {
        return definedRoutes.containsKey(request.getRoute());
    }

    private void addHeadToAllowedMethods(Request request, List<Request> allowedMethods) {
        allowedMethods.add(new Request(HEAD, request.getRoute()).thatRespondsWith(request.getDefinedResponse()));
    }

    private boolean newRequestIsGET(Request request) {
        return request.getMethod() == GET;
    }

    private static Predicate<Request> matchingMethods(Request request) {
        return route -> route.getMethod().equals(request.getMethod());
    }

}

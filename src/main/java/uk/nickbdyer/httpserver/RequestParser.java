package uk.nickbdyer.httpserver;

import java.util.*;
import java.util.stream.Collectors;

import static uk.nickbdyer.httpserver.Method.*;

public class RequestParser {

    private Map<String, List<Request>> validRequests;

    public RequestParser() {
        this.validRequests = new HashMap<>();
    }

    public void add(Request request) {
        List<Request> allowedMethods;
        if (isExistingRoute(request)) {
            allowedMethods = validRequests.get(request.getRoute());
            allowedMethods.add(request);
        } else {
            allowedMethods = new ArrayList<>(Arrays.asList(request));
            validRequests.put(request.getRoute(), allowedMethods);
        }
        if (request.getMethod() == GET) {
            allowedMethods.add(new Request(HEAD, request.getRoute()).thatRespondsWith(request.getResponse()));
        }
    }

    public Request parse(String requestString) {
        Method method = validateMethod(requestString.substring(0, (requestString.indexOf(' '))));
        String route = requestString.substring((requestString.indexOf(' ') + 1), requestString.lastIndexOf(' '));
        return new Request(method, route);
    }

    public Method validateMethod(String method) {
        try {
            return Method.valueOf(method);
        } catch (IllegalArgumentException e) {
            return METHOD_NOT_ALLOWED;
        }
    }

    public Response getResponse(Request request) {
        if (!isExistingRoute(request)) {
            return request.getResponse();
        } else if (isDefinedRequest(request)) {
            int responseIndex = validRequests.get(request.getRoute()).indexOf(request);
            return validRequests.get(request.getRoute()).get(responseIndex).getResponse();
        } else {
            List<Method> allowedMethods = validRequests.get(request.getRoute()).stream()
                    .map(Request::getMethod)
                    .collect(Collectors.toList());
            return request.thatRespondsWith(Response.MethodNotAllowed(allowedMethods)).getResponse();
        }
    }

    private boolean isDefinedRequest(Request request) {
        if (isExistingRoute(request)) {
            return validRequests.get(request.getRoute()).contains(request);
        }
        return false;
    }

    private boolean isExistingRoute(Request request) {
        return validRequests.containsKey(request.getRoute());
    }
}

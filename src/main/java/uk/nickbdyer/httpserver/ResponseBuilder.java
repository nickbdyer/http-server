package uk.nickbdyer.httpserver;

import java.util.*;
import java.util.stream.Collectors;

import static uk.nickbdyer.httpserver.Method.GET;
import static uk.nickbdyer.httpserver.Method.HEAD;

public class ResponseBuilder {

    private Map<String, List<Request>> definedRequests;

    public ResponseBuilder() {
        this.definedRequests = new HashMap<>();
    }

    public void add(Request request) {
        List<Request> allowedMethods;
        if (isExistingRoute(request)) {
            allowedMethods = definedRequests.get(request.getRoute());
            allowedMethods.add(request);
        } else {
            allowedMethods = new ArrayList<>(Arrays.asList(request));
            definedRequests.put(request.getRoute(), allowedMethods);
        }
        if (request.getMethod() == GET) {
            allowedMethods.add(new Request(HEAD, request.getRoute()).thatRespondsWith(request.getResponse()));
        }
    }

    public Response getResponse(Request request) {
        if (!isExistingRoute(request)) {
            return request.getResponse();
        } else if (isDefinedRequest(request)) {
            int responseIndex = definedRequests.get(request.getRoute()).indexOf(request);
            return definedRequests.get(request.getRoute()).get(responseIndex).getResponse();
        } else {
            List<Method> allowedMethods = definedRequests.get(request.getRoute()).stream()
                    .map(Request::getMethod)
                    .collect(Collectors.toList());
            return request.thatRespondsWith(Response.MethodNotAllowed(allowedMethods)).getResponse();
        }
    }

    private boolean isDefinedRequest(Request request) {
        if (isExistingRoute(request)) {
            return definedRequests.get(request.getRoute()).contains(request);
        }
        return false;
    }

    private boolean isExistingRoute(Request request) {
        return definedRequests.containsKey(request.getRoute());
    }

    public String getStatusLine() {
        return null;
    }
}

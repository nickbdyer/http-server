package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.Responses.MethodNotAllowed;
import uk.nickbdyer.httpserver.Responses.Response;

import java.util.*;
import java.util.stream.Collectors;

import static uk.nickbdyer.httpserver.Method.GET;
import static uk.nickbdyer.httpserver.Method.HEAD;

public class Router {

    private Map<String, List<Request>> definedRequests;

    public Router() {
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
        if (newRequestIsGET(request)) {
            addHeadToAllowedMethods(request, allowedMethods);
        }
    }

    public Response getResponse(Request request) {
        if (!isExistingRoute(request)) {
            return request.getDefinedResponse();
        } else if (isDefinedRequest(request)) {
            return definedRequests.get(request.getRoute()).stream()
                    .filter(request::equals)
                    .collect(Collectors.toList()).get(0).getDefinedResponse();
        } else {
            return new MethodNotAllowed(definedRequests.get(request.getRoute()).stream()
                    .map(Request::getMethod)
                    .collect(Collectors.toList()));
        }
    }

    private boolean isDefinedRequest(Request request) {
        return isExistingRoute(request) && definedRequests.get(request.getRoute()).contains(request);
    }

    private boolean isExistingRoute(Request request) {
        return definedRequests.containsKey(request.getRoute());
    }

    private void addHeadToAllowedMethods(Request request, List<Request> allowedMethods) {
        allowedMethods.add(new Request(HEAD, request.getRoute()).thatRespondsWith(request.getDefinedResponse()));
    }

    private boolean newRequestIsGET(Request request) {
        return request.getMethod() == GET;
    }
}

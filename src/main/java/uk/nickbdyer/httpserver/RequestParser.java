package uk.nickbdyer.httpserver;

import java.util.ArrayList;
import java.util.List;

import static uk.nickbdyer.httpserver.Method.*;

public class RequestParser {

    private final List<Request> validRequests;

    public RequestParser(List<Request> validRequests) {
        this.validRequests = addValidHeadRequests(validRequests);
    }

    public Request parse(String requestString) {
        Method method = validateMethod(requestString.substring(0, (requestString.indexOf(' '))));
        String route = requestString.substring((requestString.indexOf(' ') + 1), requestString.lastIndexOf(' '));
        return new Request(method, route);
    }


    public boolean isValid(Request request) {
        return validRequests.contains(request);
    }

    public Method validateMethod(String method) {
        try {
            return Method.valueOf(method);
        } catch (IllegalArgumentException e) {
            return Method.INVALID_METHOD;
        }
    }

    private List<Request> addValidHeadRequests(List<Request> existingRequests) {
        ArrayList<Request> newRequests = new ArrayList<>();
        for (Request request : existingRequests) {
            if (request.getMethod() == GET) {
                newRequests.add(new Request(HEAD, request.getRoute()));
            }
            newRequests.add(request);
        }
        return newRequests;
    }

}

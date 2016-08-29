package uk.nickbdyer.httpserver;

import java.util.List;

public class RequestParser {

    private final List<Request> validRequests;

    public RequestParser(List<Request> validRequests) {
        this.validRequests = validRequests;
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
}

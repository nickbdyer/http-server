package uk.nickbdyer.httpserver;

import java.util.List;

public class RequestParser {

    private final List<Request> validRequests;

    public RequestParser(List<Request> validRequests) {
        this.validRequests = validRequests;
    }

    public Request parse(String requestString) {
        String method = requestString.substring(0, (requestString.indexOf(' ')));
        String route = requestString.substring((requestString.indexOf(' ') + 1), requestString.lastIndexOf(' '));
        return new Request(Method.valueOf(method), route);
    }


    public boolean isValid(Request request) {
        return true;
    }
}

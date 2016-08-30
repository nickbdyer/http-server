package uk.nickbdyer.httpserver;

import static uk.nickbdyer.httpserver.Method.METHOD_NOT_ALLOWED;

public class RequestParser {

    public Request parse(String requestString) {
        Method method = validateMethod(requestString.substring(0, (requestString.indexOf(' '))));
        String route = requestString.substring((requestString.indexOf(' ') + 1), requestString.lastIndexOf(' '));
        return new Request(method, route);
    }

    private Method validateMethod(String method) {
        try {
            return Method.valueOf(method);
        } catch (IllegalArgumentException e) {
            return METHOD_NOT_ALLOWED;
        }
    }

}

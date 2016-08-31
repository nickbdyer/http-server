package uk.nickbdyer.httpserver;

import static uk.nickbdyer.httpserver.Method.METHOD_NOT_ALLOWED;

public class RequestParser {

    public Request parse(String requestString) {
        int firstSpace = requestString.indexOf(' ');
        Method method = validateMethod(requestString.substring(0, (firstSpace)));
        String route = requestString.substring((firstSpace + 1), requestString.indexOf(' ', firstSpace + 1));
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

package uk.nickbdyer.httpserver;

public class RequestParser {

    public Request parse(String requestString) {
        String method = requestString.substring(0, (requestString.indexOf(' ')));
        String route = requestString.substring((requestString.indexOf(' ') + 1), requestString.lastIndexOf(' '));
        return new Request(Method.valueOf(method), route);
    }


}

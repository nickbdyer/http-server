package uk.nickbdyer.httpserver;

public class RequestParser {

    public Request parse(String requestString) {
        String method = requestString.substring(0, (requestString.indexOf(' ')));
        return new Request(Method.valueOf(method));
    }


}

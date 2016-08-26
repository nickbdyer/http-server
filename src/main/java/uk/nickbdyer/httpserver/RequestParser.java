package uk.nickbdyer.httpserver;

import static uk.nickbdyer.httpserver.Method.GET;

public class RequestParser {

    public Request parse(String requestString) {
        return new Request(GET);
    }


}

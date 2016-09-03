package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

public class Controller {

    public Response execute(Request request) {
        switch (request.getMethod()) {
            case GET:
                return get(request);
            case POST:
                return post(request);
            case PUT:
                return put(request);
            case DELETE:
                return delete(request);
            case HEAD:
                return head(request);
            case OPTIONS:
                return options(request);
            case TRACE:
                return trace(request);
            case CONNECT:
                return connect(request);
            default:
                return new Response("HTTP/1.1 405 Method Not Allowed", "", "");
        }

    }

    protected Response get(Request request) {
        return new Response("HTTP/1.1 405 Method Not Allowed", "", "");
    }

    protected Response post(Request request) {
        return new Response("HTTP/1.1 405 Method Not Allowed", "", "");
    }

    protected Response put(Request request) {
        return new Response("HTTP/1.1 405 Method Not Allowed", "", "");
    }

    protected Response delete(Request request) {
        return new Response("HTTP/1.1 405 Method Not Allowed", "", "");
    }

    protected Response head(Request request) {
        return new Response("HTTP/1.1 405 Method Not Allowed", "", "");
    }

    protected Response options(Request request) {
        return new Response("HTTP/1.1 405 Method Not Allowed", "", "");
    }

    protected Response trace(Request request) {
        return new Response("HTTP/1.1 405 Method Not Allowed", "", "");
    }

    protected Response connect(Request request) {
        return new Response("HTTP/1.1 405 Method Not Allowed", "", "");
    }
}

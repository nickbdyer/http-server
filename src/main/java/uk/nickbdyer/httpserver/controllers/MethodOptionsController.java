package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

public class MethodOptionsController extends Controller {

    @Override
    public Response get(Request request) {
        return new Response("HTTP/1.1 200 OK", "", null);
    }

    @Override
    public Response head(Request request) {
        return new Response("HTTP/1.1 200 OK", "", null);
    }

    @Override
    public Response post(Request request) {
        return new Response("HTTP/1.1 200 OK", "", null);
    }

    @Override
    public Response options(Request request) {
        return new Response("HTTP/1.1 200 OK", allowedMethods(), null);
    }

    @Override
    public Response put(Request request) {
        return new Response("HTTP/1.1 200 OK", "", null);
    }

}

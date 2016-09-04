package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

public class MethodOptions2Controller extends Controller {

    @Override
    public Response get(Request request) {
        return new Response("HTTP/1.1 200 OK", "", "");
    }

    @Override
    public Response options(Request request) {
        return new Response("HTTP/1.1 200 OK", allowedMethods(), "");
    }

}

package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.Controller;
import uk.nickbdyer.httpserver.Request;
import uk.nickbdyer.httpserver.Response;

public class RootController extends Controller {

    @Override
    public Response get(Request request) {
        return new Response("HTTP/1.1 200 OK", "", null);
    }

    @Override
    public Response head(Request request) {
        return new Response("HTTP/1.1 200 OK", "", null);
    }

}

package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

public class CoffeeController extends Controller {

    @Override
    public Response get(Request request) {
        return new Response("HTTP/1.1 418 OK", "", "I'm a teapot");
    }

}

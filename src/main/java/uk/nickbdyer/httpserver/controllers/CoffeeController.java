package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

public class CoffeeController extends Controller {

    @Override
    public Response get(Request request) {
        return new Response(418, "", "I'm a teapot");
    }

}

package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import static uk.nickbdyer.httpserver.responses.StatusLine.COFFEE;

public class CoffeeController extends Controller {

    @Override
    public Response get(Request request) {
        return new Response(COFFEE, "", "I'm a teapot");
    }

}

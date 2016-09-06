package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.util.HashMap;

public class MethodOptionsController extends Controller {

    @Override
    public Response get(Request request) {
        return new Response(200, new HashMap<>(), "");
    }

    @Override
    public Response head(Request request) {
        return new Response(200, new HashMap<>(), "");
    }

    @Override
    public Response post(Request request) {
        return new Response(200, new HashMap<>(), "");
    }

    @Override
    public Response options(Request request) {
        return new Response(200, allowedMethods(), "");
    }

    @Override
    public Response put(Request request) {
        return new Response(200, new HashMap<>(), "");
    }

}

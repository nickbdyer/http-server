package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import static uk.nickbdyer.httpserver.responses.StatusLine.OK;

public class MethodOptionsController extends Controller {

    @Override
    public Response get(Request request) {
        return new Response(OK, "", "");
    }

    @Override
    public Response head(Request request) {
        return new Response(OK, "", "");
    }

    @Override
    public Response post(Request request) {
        return new Response(OK, "", "");
    }

    @Override
    public Response options(Request request) {
        return new Response(OK, allowedMethods(), "");
    }

    @Override
    public Response put(Request request) {
        return new Response(OK, "", "");
    }

}

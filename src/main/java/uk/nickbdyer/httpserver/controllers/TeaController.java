package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import static uk.nickbdyer.httpserver.responses.StatusLine.OK;

public class TeaController extends Controller {

    @Override
    public Response get(Request request) {
        return new Response(OK, "", "Short and stout");
    }

}

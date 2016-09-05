package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

public class TeaController extends Controller {

    @Override
    public Response get(Request request) {
        return new Response(200, "", "Short and stout");
    }

}

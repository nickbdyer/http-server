package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import static uk.nickbdyer.httpserver.responses.StatusLine.REDIRECT;


public class RedirectController extends Controller {

    @Override
    public Response get(Request request) {
        String header = "Location: http://localhost:5000/\n";
        return new Response(REDIRECT, header, "");
    }

}

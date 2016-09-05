package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;


public class RedirectController extends Controller {

    @Override
    public Response get(Request request) {
        String header = "Location: http://localhost:5000/\n";
        return new Response(302, header, "");
    }

}

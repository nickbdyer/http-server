package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.Controller;
import uk.nickbdyer.httpserver.Request;
import uk.nickbdyer.httpserver.Response;


public class RedirectController extends Controller {

    @Override
    public Response get(Request request) {
        return new Response("HTTP/1.1 302 Found", "", null);
    }

}

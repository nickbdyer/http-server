package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.middleware.BasicAuth;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.util.HashMap;

public class LogsController extends Controller {

    private final BasicAuth basicAuthorisor;

    public LogsController(BasicAuth basicAuthorisor) {
        this.basicAuthorisor = basicAuthorisor;
    }

    @Override
    public Response get(Request request) {
        String user = request.getHeaders().getOrDefault("Authorization", "");
        if (basicAuthorisor.userIsAuthorised(user)) {
            return new Response(200, new HashMap<>(), "");
        }
        return new Response(401, basicAuthorisor.getUnAuthorisedHeader(), "");
    }

}

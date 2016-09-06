package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.util.HashMap;
import java.util.stream.Collectors;

public class ParameterController extends Controller {

    @Override
    public Response get(Request request) {
        String body = formatParamsAsString(request);
        return new Response(200, new HashMap<>(), body);
    }

    private String formatParamsAsString(Request request) {
        String body = null;
        if (request.getParameters() != null) {
            body = request.getParameters().entrySet().stream()
                    .map(entry -> entry.getKey() + " = " + entry.getValue() + "\n")
                    .collect(Collectors.joining());
        }
        return body;
    }
}

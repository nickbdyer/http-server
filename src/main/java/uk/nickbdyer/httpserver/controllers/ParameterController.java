package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.io.UnsupportedEncodingException;

import static java.net.URLDecoder.*;

public class ParameterController extends Controller {

    @Override
    public Response get(Request request) {
        String body = formatParams(request);
        return new Response("HTTP/1.1 200 OK", "", body);
    }

    private String formatParams(Request request) {
        String body = null;
        if (request.getParameters() != null) {
            try {
                body = request.getParameters().replace('&', '\n').replace("=", " = ");
                body = decode(body, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return body;
    }
}

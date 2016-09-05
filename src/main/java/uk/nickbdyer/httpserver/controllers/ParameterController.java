package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.io.UnsupportedEncodingException;

import static java.net.URLDecoder.decode;

public class ParameterController extends Controller {

    @Override
    public Response get(Request request) {
        String body = formatParamsAsString(request);
        return new Response(200, "", body);
    }

    private String formatParamsAsString(Request request) {
        String body = null;
        if (request.getParameters() != null) {
            try {
                body = request.getParameters().replace('&', '\n').replace("=", " = ");
                body = decode(body, "UTF-8");
            } catch (UnsupportedEncodingException|IllegalArgumentException e) {
                return "";
            }
        }
        return body;
    }
}

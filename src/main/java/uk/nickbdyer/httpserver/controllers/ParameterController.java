package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.exceptions.FailedDecodingException;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.io.UnsupportedEncodingException;

import static java.net.URLDecoder.decode;
import static uk.nickbdyer.httpserver.responses.StatusLine.OK;

public class ParameterController extends Controller {

    @Override
    public Response get(Request request) {
        String body = formatParamsAsString(request);
        return new Response(OK, "", body);
    }

    private String formatParamsAsString(Request request) {
        String body = null;
        if (request.getParameters() != null) {
            try {
                body = request.getParameters().replace('&', '\n').replace("=", " = ");
                body = decode(body, "UTF-8");
            } catch (UnsupportedEncodingException|IllegalArgumentException e) {
                throw new FailedDecodingException();
            }
        }
        return body;
    }
}

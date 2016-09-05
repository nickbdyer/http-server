package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.util.HashMap;

public class FormController extends Controller {

    private final FormData data;

    public FormController(FormData data) {
        this.data = data;
    }

    @Override
    public Response get(Request request) {
        return new Response(200, new HashMap<>(), data.getData());
    }

    @Override
    public Response post(Request request) {
        data.setData(request.getBody());
        return new Response(200, new HashMap<>(), data.getData());
    }

    @Override
    public Response put(Request request) {
        data.setData(request.getBody());
        return new Response(200, new HashMap<>(), data.getData());
    }

    @Override
    public Response delete(Request request) {
        data.setData("");
        return new Response(200, new HashMap<>(), data.getData());
    }
}

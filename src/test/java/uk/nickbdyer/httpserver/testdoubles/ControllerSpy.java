package uk.nickbdyer.httpserver.testdoubles;

import uk.nickbdyer.httpserver.Controller;
import uk.nickbdyer.httpserver.Request;
import uk.nickbdyer.httpserver.Response;

public class ControllerSpy extends Controller {

    public String methodTriggered;

    public ControllerSpy() {

        this.methodTriggered = "";
    }

    @Override
    public Response get(Request request) {
        methodTriggered = "get";
        return null;
    }

    @Override
    public Response post(Request request) {
        methodTriggered = "post";
        return null;
    }

    @Override
    public Response put(Request request) {
        methodTriggered = "put";
        return null;
    }

    @Override
    public Response delete(Request request) {
        methodTriggered = "delete";
        return null;
    }

    @Override
    public Response head(Request request) {
        methodTriggered = "head";
        return null;
    }

    @Override
    public Response options(Request request) {
        methodTriggered = "options";
        return null;
    }

    @Override
    public Response trace(Request request) {
        methodTriggered = "trace";
        return null;
    }

    @Override
    public Response connect(Request request) {
        methodTriggered = "connect";
        return null;
    }
}

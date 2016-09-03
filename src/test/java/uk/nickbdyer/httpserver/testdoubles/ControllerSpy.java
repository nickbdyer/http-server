package uk.nickbdyer.httpserver.testdoubles;

import uk.nickbdyer.httpserver.controllers.Controller;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

public class ControllerSpy extends Controller {

    public String methodTriggered;

    public ControllerSpy() {

        this.methodTriggered = "";
    }

    @Override
    public Response get(Request request) {
        methodTriggered = "get";
        return super.get(request);
    }

    @Override
    public Response post(Request request) {
        methodTriggered = "post";
        return super.post(request);
    }

    @Override
    public Response put(Request request) {
        methodTriggered = "put";
        return super.put(request);
    }

    @Override
    public Response delete(Request request) {
        methodTriggered = "delete";
        return super.delete(request);
    }

    @Override
    public Response head(Request request) {
        methodTriggered = "head";
        return super.head(request);
    }

    @Override
    public Response options(Request request) {
        methodTriggered = "options";
        return super.options(request);
    }

    @Override
    public Response trace(Request request) {
        methodTriggered = "trace";
        return super.trace(request);
    }

    @Override
    public Response connect(Request request) {
        methodTriggered = "connect";
        return super.connect(request);
    }
}

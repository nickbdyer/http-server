package uk.nickbdyer.httpserver;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.Method.*;
import static uk.nickbdyer.httpserver.ConcreteResponse.*;

public class ResponseBuilderTest {

    @Test
    public void builderWillReturnADefinedResponseIfItExists() {
        Request request = new Request(GET, "/").thatRespondsWith(OK());
        ResponseBuilder builder = new ResponseBuilder();
        builder.add(request);

        assertEquals(OK(), builder.getResponse(getRequest()));
    }

    @Test
    public void builderWillReturnANotFoundResponseIfNoDefinedResponseIsFound() {
        Request request = new Request(GET, "/");
        ResponseBuilder builder = new ResponseBuilder();
        builder.add(request);

        assertEquals(NotFound(), builder.getResponse(getRequest()));
    }

    @Test
    public void builderWillReturnANotFoundResponseIfRequestIsNotValid() {
        ResponseBuilder builder = new ResponseBuilder();

        assertEquals(NotFound(), builder.getResponse(getRequest()));
    }

    @Test
    public void builderWillReturnAMethodNotAllowedResponseIfMethodIsNotAllowed() {
        Request request = new Request(GET, "/").thatRespondsWith(OK());
        ResponseBuilder builder = new ResponseBuilder();
        builder.add(request);

        assertEquals(MethodNotAllowed(new ArrayList<>()), builder.getResponse(postRequest()));
    }

    @Test
    public void builderWillReturnAMethodNotAllowedResponseIfMethodIsNonSensical() {
        Request request = new Request(GET, "/").thatRespondsWith(OK());
        ResponseBuilder builder = new ResponseBuilder();
        builder.add(request);

        assertEquals(MethodNotAllowed(new ArrayList<>()), builder.getResponse(notAllowedRequest()));
    }

    private Request getRequest() {
        return new Request(GET, "/");
    }

    private Request postRequest() {
        return new Request(POST, "/");
    }

    private Request notAllowedRequest() {
        return new Request(METHOD_NOT_ALLOWED, "/");
    }

}

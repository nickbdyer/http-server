package uk.nickbdyer.httpserver;

import org.junit.Test;
import uk.nickbdyer.httpserver.Responses.MethodNotAllowed;
import uk.nickbdyer.httpserver.Responses.NotFound;
import uk.nickbdyer.httpserver.Responses.OK;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.Method.*;

public class RouterTest {

    @Test
    public void builderWillReturnADefinedResponseIfItExists() {
        Request request = new Request(GET, "/").thatRespondsWith(new OK());
        Router builder = new Router();
        builder.add(request);

        assertEquals(new OK().getResponse(), builder.getResponse(getRequest()).getResponse());
    }

    @Test
    public void builderWillReturnANotFoundResponseIfNoDefinedResponseIsFound() {
        Request request = new Request(GET, "/");
        Router builder = new Router();
        builder.add(request);

        assertEquals(new NotFound().getResponse(), builder.getResponse(getRequest()).getResponse());
    }

    @Test
    public void builderWillReturnANotFoundResponseIfRequestIsNotValid() {
        Router builder = new Router();

        assertEquals(new NotFound().getResponse(), builder.getResponse(getRequest()).getResponse());
    }

    @Test
    public void builderWillReturnAMethodNotAllowedResponseIfMethodIsNotAllowed() {
        Request request = new Request(GET, "/").thatRespondsWith(new OK());
        Router builder = new Router();
        builder.add(request);

        assertEquals(new MethodNotAllowed(new ArrayList<>(asList(GET, HEAD))).getResponse(), builder.getResponse(postRequest()).getResponse());
    }

    @Test
    public void builderWillReturnAMethodNotAllowedResponseIfMethodIsNonSensical() {
        Request request = new Request(GET, "/").thatRespondsWith(new OK());
        Router builder = new Router();
        builder.add(request);

        assertEquals(new MethodNotAllowed(new ArrayList<>(asList(GET, HEAD))).getResponse(), builder.getResponse(notAllowedRequest()).getResponse());
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

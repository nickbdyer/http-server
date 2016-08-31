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
    public void routerWillReturnADefinedResponseIfItExists() {
        Request request = new Request(GET, "/").thatRespondsWith(new OK());
        Router router = new Router();
        router.add(request);

        assertEquals(new OK().getStatusLine(), router.getResponse(getRequest()).getStatusLine());
    }

    @Test
    public void routerWillReturnANotFoundResponseIfNoDefinedResponseIsFound() {
        Request request = new Request(GET, "/");
        Router router = new Router();
        router.add(request);

        assertEquals(new NotFound().getStatusLine(), router.getResponse(getRequest()).getStatusLine());
    }

    @Test
    public void routerWillReturnANotFoundResponseIfRequestIsNotValid() {
        Router router = new Router();

        assertEquals(new NotFound().getResponse(), router.getResponse(getRequest()).getResponse());
    }

    @Test
    public void routerWillReturnAMethodNotAllowedResponseIfMethodIsNotAllowed() {
        Request request = new Request(GET, "/").thatRespondsWith(new OK());
        Router router = new Router();
        router.add(request);

        assertEquals(new MethodNotAllowed(new ArrayList<>(asList(GET, HEAD))).getResponse(), router.getResponse(postRequest()).getResponse());
    }

    @Test
    public void routerWillReturnAMethodNotAllowedResponseIfMethodIsNonSensical() {
        Request request = new Request(GET, "/").thatRespondsWith(new OK());
        Router router = new Router();
        router.add(request);

        assertEquals(new MethodNotAllowed(new ArrayList<>(asList(GET, HEAD))).getResponse(), router.getResponse(notAllowedRequest()).getResponse());
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

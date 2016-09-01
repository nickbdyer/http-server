package uk.nickbdyer.httpserver;

import org.junit.Test;
import uk.nickbdyer.httpserver.Responses.MethodNotAllowed;
import uk.nickbdyer.httpserver.Responses.NotFound;
import uk.nickbdyer.httpserver.Responses.OK;
import uk.nickbdyer.httpserver.Responses.Redirect;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.Method.*;

public class RouterTest {

    @Test
    public void routeWillReturnAnOKResponseToAKnownRoute() {
        Route route = new Route(GET, "/").thatRespondsWith(new OK());
        Router router = new Router();
        router.add(route);

        assertEquals(new OK().getStatusLine(), router.getResponse(getRequest()).getStatusLine());
    }

    @Test
    public void routerWillReturnANotFoundResponseIfNoDefinedResponseIsFound() {
        Route route = new Route(GET, "/");
        Router router = new Router();
        router.add(route);

        assertEquals(new NotFound().getStatusLine(), router.getResponse(getRequest()).getStatusLine());
    }

    @Test
    public void routerWillReturnANotFoundResponseIfRequestIsNotValid() {
        Router router = new Router();

        assertEquals(new NotFound().getResponse(), router.getResponse(getRequest()).getResponse());
    }

    @Test
    public void routerWillReturnAMethodNotAllowedResponseIfMethodIsNotAllowed() {
        Route route = new Route(GET, "/").thatRespondsWith(new OK());
        Route route2 = new Route(PUT, "/").thatRespondsWith(new Redirect(""));
        Router router = new Router();
        router.add(route);
        router.add(route2);

        assertEquals(new MethodNotAllowed(new ArrayList<>(asList(GET, HEAD, PUT))).getResponse(), router.getResponse(postRequest()).getResponse());
    }

    @Test
    public void routerWillReturnAMethodNotAllowedResponseIfMethodIsNonsensical() {
        Route route = new Route(GET, "/").thatRespondsWith(new OK());
        Router router = new Router();
        router.add(route);

        assertEquals(new MethodNotAllowed(new ArrayList<>(asList(GET, HEAD))).getResponse(), router.getResponse(notAllowedRequest()).getResponse());
   }

    private Request getRequest() {
        return new Request(GET, "/");
    }

    private Request postRequest() {
        return new Request(POST, "/");
    }

    private Request notAllowedRequest() {
        return new Request(INVALID_METHOD, "/");
    }

}

package uk.nickbdyer.httpserver;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static uk.nickbdyer.httpserver.Method.*;

public class RouterTest {

    @Test
    public void routeWillReturnAnOKResponseToAKnownRoute() {
        Route route = new Route(GET, "/").thatRespondsWith(OK());
        Router router = new Router();
        router.add(route);

        assertEquals(OK().getStatusLine(), router.getResponse(getRequest()).getStatusLine());
    }

    @Test
    public void routerWillReturnANotFoundResponseIfNoDefinedResponseIsFound() {
        Route route = new Route(GET, "/");
        Router router = new Router();
        router.add(route);

        assertEquals(NotFound().getStatusLine(), router.getResponse(getRequest()).getStatusLine());
    }

    @Test
    public void routerWillReturnANotFoundResponseIfRequestIsNotValid() {
        Router router = new Router();

        assertEquals(NotFound().getResponse(), router.getResponse(getRequest()).getResponse());
    }

    @Test
    public void routerWillReturnAMethodNotAllowedResponseIfMethodIsNotAllowed() {
        Route route = new Route(GET, "/").thatRespondsWith(OK());
        Route route2 = new Route(PUT, "/").thatRespondsWith(Redirect(""));
        Router router = new Router();
        router.add(route);
        router.add(route2);

        assertEquals(MethodNotAllowed(new ArrayList<>(asList(GET, HEAD, PUT))).getResponse(), router.getResponse(postRequest()).getResponse());
    }

    @Test
    public void routerWillReturnAMethodNotAllowedResponseIfMethodIsNonsensical() {
        Route route = new Route(GET, "/").thatRespondsWith(OK());
        Router router = new Router();
        router.add(route);

        assertEquals(MethodNotAllowed(new ArrayList<>(asList(GET, HEAD))).getResponse(), router.getResponse(notAllowedRequest()).getResponse());
    }

    @Test
    public void aRedirectResponseCanAlsoHaveADefinedLocation() {
        Response response = Redirect("another location");

        assertThat(response.getResponse(), containsString("Location: another location"));
    }

    @Test
    public void aMethodNotAllowedResponseCanListAllowedMethods() {
        Response response = MethodNotAllowed(new ArrayList<>(Arrays.asList(GET, POST, HEAD)));

        assertThat(response.getResponse(), containsString("Allow: GET, POST, HEAD\n"));
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

    private Response OK() {
        return new Response("HTTP/1.1 200 OK", "", "");
    }

    private Response Redirect(String location) {
        String headers = new Router().createResponseHeader(location);
        return new Response("HTTP/1.1 302 Found", headers, "");
    }

    private Response NotFound() {
        return new Response("HTTP/1.1 404 Not Found", "", "");
    }

    private Response MethodNotAllowed(List<Method> allowedMethods) {
        String headers = new Router().createResponseHeader(allowedMethods);
        return new Response("HTTP/1.1 405 Method Not Allowed", headers, "");

    }

}

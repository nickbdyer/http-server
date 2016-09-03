package uk.nickbdyer.httpserver;

import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.httpserver.testdoubles.ControllerSpy;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static uk.nickbdyer.httpserver.Method.*;

public class RouterTest {

    private Router router;

    @Before
    public void setUp() {
        router = new Router(new File(""));
    }

    @Test
    public void aRouterWillPassARequestToTheCorrectController() {
        ControllerSpy controller = new ControllerSpy();
        router.addController("/test", controller);
        Request request = new Request(GET, "/test");

        router.getControllerResponse(request);

        assertEquals("get", controller.methodTriggered);
    }

    @Test
    public void aRouterWillReturnNotFoundIfNoControllerExistsForAPath() {
        Request request = new Request(GET, "/test");

        Response response = router.getControllerResponse(request);

        assertEquals("HTTP/1.1 404 Not Found\n", response.getStatusLine());
    }

    @Test
    public void routeWillReturnAnOKResponseToAKnownRoute() {
        Route route = new Route(GET, "/");
        router.add(route);

        assertEquals(OK().getStatusLine(), router.getResponse(getRequest()).getStatusLine());
    }

    @Test
    public void routerWillReturnANotFoundResponseIfRequestIsNotValid() {
        assertEquals(NotFound().getResponse(), router.getResponse(getRequest()).getResponse());
    }

    @Test
    public void routerWillReturnAMethodNotAllowedResponseIfMethodIsNotAllowed() {
        Route route = new Route(GET, "/");
        Route route2 = new Route(PUT, "/");
        router.add(route);
        router.add(route2);

        assertEquals(MethodNotAllowed(new ArrayList<>(asList(GET, HEAD, PUT))).getResponse(), router.getResponse(postRequest()).getResponse());
    }

    @Test
    public void routerWillReturnAMethodNotAllowedResponseIfMethodIsNonsensical() {
        Route route = new Route(GET, "/");
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
        return new Response("HTTP/1.1 200 OK", "", null);
    }

    private Response Redirect(String location) {
        String headers = new Router(new File("")).createResponseHeader(location);
        return new Response("HTTP/1.1 302 Found", headers, null);
    }

    private Response NotFound() {
        return new Response("HTTP/1.1 404 Not Found", "", null);
    }

    private Response MethodNotAllowed(List<Method> allowedMethods) {
        String headers = new Router(new File("")).createResponseHeader(allowedMethods);
        return new Response("HTTP/1.1 405 Method Not Allowed", headers, null);

    }

}

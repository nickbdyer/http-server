package uk.nickbdyer.httpserver;

import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;
import uk.nickbdyer.httpserver.testdoubles.ControllerSpy;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.requests.Method.GET;

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

        router.route(request);

        assertEquals("get", controller.methodTriggered);
    }

    @Test
    public void aRouterWillReturnNotFoundIfNoControllerExistsForAPath() {
        Request request = new Request(GET, "/test");

        Response response = router.route(request);

        assertEquals("HTTP/1.1 404 Not Found\n", response.getStatusLine());
    }

}

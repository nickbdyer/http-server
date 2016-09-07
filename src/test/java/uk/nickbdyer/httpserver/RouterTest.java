package uk.nickbdyer.httpserver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.requests.RequestLine;
import uk.nickbdyer.httpserver.responses.Response;
import uk.nickbdyer.httpserver.testdoubles.ControllerSpy;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.requests.Method.GET;

public class RouterTest {

    private Router router;
    private Request request;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        router = new Router(tempFolder.getRoot());
        request = new Request(new RequestLine(GET, "/test", new HashMap<>()), new HashMap<>(), "");
    }

    @Test
    public void routerWillMatchARequestPathToInTheRouteTableAndTriggerTheController() {
        ControllerSpy controller = new ControllerSpy();
        router.addController("/test", controller);

        router.route(request);

        assertEquals("get", controller.methodTriggered);
    }

    @Test
    public void routerWillReturnNotFoundIfPathCanNotBeMatched() {
        Response response = router.route(request);

        assertEquals(404, response.getStatusCode());
    }

    @Test
    public void routerWillDynamicallyAddARouteForAFileFoundInThePublicFolder() throws IOException {
        tempFolder.newFile("test");

        Response response = router.route(request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void routerWillReturnNotFoundForPreloadRequestsWithoutHeaders() throws IOException {
        Request request = new Request(null, null);

        Response response = router.route(request);

        assertEquals(404, response.getStatusCode());
    }

}

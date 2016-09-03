package uk.nickbdyer.httpserver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;
import uk.nickbdyer.httpserver.testdoubles.ControllerSpy;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.requests.Method.GET;

public class RouterTest {

    private Router router;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        router = new Router(tempFolder.getRoot());
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

    @Test
    public void aRouterWillPassARequestToTheFileControllerIfAFileExists() throws IOException {
        tempFolder.newFile("test");
        Request request = new Request(GET, "/test");

        Response response = router.route(request);

        assertEquals("HTTP/1.1 200 OK\n", response.getStatusLine());
    }

}

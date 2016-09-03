package uk.nickbdyer.httpserver.controllers;

import org.junit.Test;
import uk.nickbdyer.httpserver.Request;
import uk.nickbdyer.httpserver.Response;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.Method.GET;
import static uk.nickbdyer.httpserver.Method.HEAD;

public class RootControllerTest {

    @Test
    public void willRespondToAGetRequest() {
        RootController controller = new RootController();
        Request request = new Request(GET, "/");

        Response response = controller.execute(request);

        assertEquals("HTTP/1.1 200 OK\n", response.getStatusLine());
        assertEquals(null, response.getResponseBody());
    }

    @Test
    public void willRespondToAHeadRequest() {
        RootController controller = new RootController();
        Request request = new Request(HEAD, "/");

        Response response = controller.execute(request);

        assertEquals("HTTP/1.1 200 OK\n", response.getStatusLine());
        assertEquals(null, response.getResponseBody());
    }
}

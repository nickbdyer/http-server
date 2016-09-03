package uk.nickbdyer.httpserver.controllers;

import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.requests.Method.GET;
import static uk.nickbdyer.httpserver.requests.Method.HEAD;

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

package uk.nickbdyer.httpserver.controllers;

import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static uk.nickbdyer.httpserver.requests.Method.*;

public class MethodOptionsControllerTest {

    @Test
    public void willRespondToAGetRequest() {
        MethodOptionsController controller = new MethodOptionsController();
        Request request = new Request(GET, "/");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void willRespondToAHeadRequest() {
        MethodOptionsController controller = new MethodOptionsController();
        Request request = new Request(HEAD, "/");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void willRespondToAPostRequest() {
        MethodOptionsController controller = new MethodOptionsController();
        Request request = new Request(POST, "/");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void willRespondToAOptionsRequest() {
        MethodOptionsController controller = new MethodOptionsController();
        Request request = new Request(OPTIONS, "/");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
        assertTrue(response.getHeaders().containsKey("Allow: "));
        assertEquals("GET,HEAD,OPTIONS,POST,PUT\n", response.getHeaders().get("Allow: "));
    }

    @Test
    public void willRespondToAPutRequest() {
        MethodOptionsController controller = new MethodOptionsController();
        Request request = new Request(PUT, "/");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
    }

}

package uk.nickbdyer.httpserver.controllers;

import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.requests.Method.*;

public class FormControllerTest {

    @Test
    public void willRespondToAGetRequest() {
        FormData data = new FormData(null);
        FormController controller = new FormController(data);
        Request request = new Request(GET, "/form");

        Response response = controller.execute(request);

        assertEquals("HTTP/1.1 200 OK\n", response.getStatusLine());
        assertEquals(null, response.getResponseBody());
    }

    @Test
    public void willRespondToAPostRequest() {
        FormData data = new FormData(null);
        FormController controller = new FormController(data);
        Request request = new Request(POST, "/form", "data=fatcat");

        Response response = controller.execute(request);

        assertEquals("HTTP/1.1 200 OK\n", response.getStatusLine());
        assertEquals("data=fatcat\n", response.getResponseBody());
    }

    @Test
    public void willRespondToAPutRequest() {
        FormData data = new FormData(null);
        FormController controller = new FormController(data);
        Request request = new Request(PUT, "/form", "data=heathcliff");

        Response response = controller.execute(request);

        assertEquals("HTTP/1.1 200 OK\n", response.getStatusLine());
        assertEquals("data=heathcliff\n", response.getResponseBody());
    }

    @Test
    public void willRespondToADeleteRequest() {
        FormData data = new FormData("data=asdl;vhaw;ejkrasdf");
        FormController controller = new FormController(data);
        Request request = new Request(DELETE, "/form");

        Response response = controller.execute(request);

        assertEquals("HTTP/1.1 200 OK\n", response.getStatusLine());
        assertEquals(null, response.getResponseBody());
    }
}

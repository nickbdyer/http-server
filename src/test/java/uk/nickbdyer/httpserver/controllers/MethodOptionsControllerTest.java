package uk.nickbdyer.httpserver.controllers;

import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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
        assertThat(response.getHeader(), containsString("Allow: "));
        assertThat(response.getHeader(), containsString("GET,HEAD,OPTIONS,POST,PUT"));
    }

    @Test
    public void willRespondToAPutRequest() {
        MethodOptionsController controller = new MethodOptionsController();
        Request request = new Request(PUT, "/");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
    }

}

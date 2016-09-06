package uk.nickbdyer.httpserver.controllers;

import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static uk.nickbdyer.httpserver.requests.Method.GET;
import static uk.nickbdyer.httpserver.requests.Method.OPTIONS;

public class MethodOptions2ControllerTest {

    @Test
    public void willRespondToAGetRequest() {
        MethodOptions2Controller controller = new MethodOptions2Controller();
        Request request = new Request(GET, "/");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void willRespondToAOptionsRequest() {
        MethodOptions2Controller controller = new MethodOptions2Controller();
        Request request = new Request(OPTIONS, "/");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
        assertTrue(response.getHeaders().containsKey("Allow: "));
        assertThat(response.getHeaders().get("Allow: "), containsString("GET,OPTIONS\n"));
    }

}

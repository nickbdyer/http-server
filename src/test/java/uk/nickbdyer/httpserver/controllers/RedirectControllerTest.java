package uk.nickbdyer.httpserver.controllers;

import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.requests.Method.GET;

public class RedirectControllerTest {

    @Test
    public void willRespondToAGetRequest() {
        RedirectController controller = new RedirectController();
        Request request = new Request(GET, "/redirect");

        Response response = controller.execute(request);

        assertEquals("HTTP/1.1 302 Found\n", response.getStatusLine());
        assertEquals(null, response.getResponseBody());
    }

}
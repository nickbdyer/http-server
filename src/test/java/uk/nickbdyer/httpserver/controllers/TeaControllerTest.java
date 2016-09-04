package uk.nickbdyer.httpserver.controllers;

import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.requests.Method.GET;

public class TeaControllerTest {

    @Test
    public void willRespondToAGetRequest() {
        TeaController controller = new TeaController();
        Request request = new Request(GET, "/");

        Response response = controller.execute(request);

        assertEquals("HTTP/1.1 200 OK\n", response.getStatusLine());
    }

}

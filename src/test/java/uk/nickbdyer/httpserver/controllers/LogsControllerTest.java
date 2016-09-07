package uk.nickbdyer.httpserver.controllers;

import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.requests.Method.GET;

public class LogsControllerTest {

    @Test
    public void willRespondToAGetRequest() {
        LogsController controller = new LogsController();
        Request request = new Request(GET, "/logs");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
    }
}

package uk.nickbdyer.httpserver.controllers;

import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static uk.nickbdyer.httpserver.requests.Method.GET;

public class CoffeeControllerTest {

    @Test
    public void willRespondToAGetRequest() {
        CoffeeController controller = new CoffeeController();
        Request request = new Request(GET, "/");

        Response response = controller.execute(request);

        assertEquals("HTTP/1.1 418 OK\n", response.getStatusLine());
    }

    @Test
    public void bodyWillContainImATeaPot() {
        CoffeeController controller = new CoffeeController();
        Request request = new Request(GET, "/");

        Response response = controller.execute(request);

        assertThat(response.getResponseBody(), containsString("I'm a teapot"));
    }
}

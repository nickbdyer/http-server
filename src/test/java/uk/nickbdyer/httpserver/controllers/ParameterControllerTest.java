package uk.nickbdyer.httpserver.controllers;

import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.requests.RequestLine;
import uk.nickbdyer.httpserver.responses.Response;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.requests.Method.GET;

public class ParameterControllerTest {

    @Test
    public void willRespondToAGetRequest() {
        ParameterController controller = new ParameterController();
        Map<String, String> params = new HashMap<>();
        params.put("variable_1", "value");
        params.put("variable_2", "othervalue");
        RequestLine line = new RequestLine(GET, "/parameters", params);
        Request request = new Request(line, new HashMap<>(), "");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("variable_1 = value\nvariable_2 = othervalue\n", new String(response.getBody()));
    }

}

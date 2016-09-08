package uk.nickbdyer.httpserver.controllers;

import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.httpserver.middleware.BasicAuth;
import uk.nickbdyer.httpserver.middleware.Logger;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.requests.RequestLine;
import uk.nickbdyer.httpserver.responses.Response;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import static uk.nickbdyer.httpserver.requests.Method.GET;

public class LogsControllerTest {

    private BasicAuth basicAuthorisor;
    private LogsController controller;
    private Logger logger;

    @Before
    public void setUp() {
        basicAuthorisor = new BasicAuth();
        basicAuthorisor.addAuthorisedUser("admin", "hunter2");
        logger = new Logger();
        logger.log("GET /route HTTP/1.1");
        controller = new LogsController(basicAuthorisor, logger);
    }

    @Test
    public void willRespondToAnUnAuthorisedGetRequest() {
        RequestLine requestLine = new RequestLine(GET, "/logs", new HashMap<>());
        Request request = new Request(requestLine, new HashMap<>(), "");

        Response response = controller.execute(request);

        assertEquals(401, response.getStatusCode());
    }

    @Test
    public void willSendAnAuthorisationHeaderToAnUnAuthorisedRequest() {
        RequestLine requestLine = new RequestLine(GET, "/logs", new HashMap<>());
        Request request = new Request(requestLine, new HashMap<>(), "");

        Response response = controller.execute(request);

        assertTrue(response.getHeaders().containsKey("WWW-Authenticate"));
    }

    @Test
    public void willRespondToAnAuthorisedGetRequest() {
        RequestLine requestLine = new RequestLine(GET, "/logs", new HashMap<>());
        Request request = new Request(requestLine, headerWithEncodedUserAndPassword("admin", "hunter2"), "");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void willReturnLogsWithAnAuthorisedRequest() {
        RequestLine requestLine = new RequestLine(GET, "/logs", new HashMap<>());
        Request request = new Request(requestLine, headerWithEncodedUserAndPassword("admin", "hunter2"), "");

        Response response = controller.execute(request);

        assertThat(new String(response.getBody()), containsString("GET /route HTTP/1.1"));
    }

    private Map<String, String> headerWithEncodedUserAndPassword(String user, String pass) {
        String encodedAuth = Base64.getEncoder().encodeToString((user + ":" + pass).getBytes());
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", ("Basic " + encodedAuth));
        return headers;
    }
}

package uk.nickbdyer.httpserver.controllers;

import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.requests.RequestParser;
import uk.nickbdyer.httpserver.responses.Response;
import uk.nickbdyer.httpserver.testdoubles.ControllerSpy;
import uk.nickbdyer.httpserver.testdoubles.DummyLogger;
import uk.nickbdyer.httpserver.testdoubles.SocketStubWithRequest;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static uk.nickbdyer.httpserver.requests.Method.*;

public class ControllerTest {

    private DummyLogger logger;

    @Before
    public void setUp() {
        logger = new DummyLogger();
    }

    @Test
    public void aControllerWillRespondToAGetRequest() {
        Request request = new Request(GET, "/");
        ControllerSpy controller = new ControllerSpy();

        Response response = controller.execute(request);

        assertEquals("get", controller.methodTriggered);
        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void aControllerWillRespondToAPostRequest() {
        Request request = new Request(POST, "/");
        ControllerSpy controller = new ControllerSpy();

        Response response = controller.execute(request);

        assertEquals("post", controller.methodTriggered);
        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void aControllerWillRespondToAPutRequest() {
        Request request = new Request(PUT, "/");
        ControllerSpy controller = new ControllerSpy();

        Response response = controller.execute(request);

        assertEquals("put", controller.methodTriggered);
        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void aControllerWillRespondToADeleteRequest() {
        Request request = new Request(DELETE, "/");
        ControllerSpy controller = new ControllerSpy();

        Response response = controller.execute(request);

        assertEquals("delete", controller.methodTriggered);
        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void aControllerWillRespondToAHeadRequest() {
        Request request = new Request(HEAD, "/");
        ControllerSpy controller = new ControllerSpy();

        Response response = controller.execute(request);

        assertEquals("head", controller.methodTriggered);
        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void aControllerWillRespondToAOptionsRequest() {
        Request request = new Request(OPTIONS, "/");
        ControllerSpy controller = new ControllerSpy();

        Response response = controller.execute(request);

        assertEquals("options", controller.methodTriggered);
        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void aControllerWillRespondToATraceRequest() {
        Request request = new Request(TRACE, "/");
        ControllerSpy controller = new ControllerSpy();

        Response response = controller.execute(request);

        assertEquals("trace", controller.methodTriggered);
        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void aControllerWillRespondToAConnectRequest() {
        Request request = new Request(CONNECT, "/");
        ControllerSpy controller = new ControllerSpy();

        Response response = controller.execute(request);

        assertEquals("connect", controller.methodTriggered);
        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void aControllerWillRespondToAPatchRequest() {
        Request request = new Request(PATCH, "/");
        ControllerSpy controller = new ControllerSpy();

        Response response = controller.execute(request);

        assertEquals("patch", controller.methodTriggered);
        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void aControllerWillRespondToAnUnknownMethodWithAMethodNotAllowedResponse() throws IOException {
        SocketStubWithRequest socket = new SocketStubWithRequest("HELLO / HTTP/1.1");
        Request request = new RequestParser(socket, logger).parse();
        ControllerSpy controller = new ControllerSpy();

        Response response = controller.execute(request);

        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void aControllerWillRespondWithACorrectHeaderForAMethodNotAllowedResponse() throws IOException {
        SocketStubWithRequest socket = new SocketStubWithRequest("HELLO / HTTP/1.1");
        Request request = new RequestParser(socket, logger).parse();
        ControllerSpy controller = new ControllerSpy();

        Response response = controller.execute(request);

        assertTrue(response.getHeaders().containsKey("Allow"));
        assertThat(response.getHeaders().get("Allow"), containsString("CONNECT,DELETE,GET,HEAD,OPTIONS,PATCH,POST,PUT,TRACE"));
    }


}


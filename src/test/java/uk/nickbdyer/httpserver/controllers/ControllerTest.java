package uk.nickbdyer.httpserver.controllers;

import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.requests.RequestParser;
import uk.nickbdyer.httpserver.responses.Response;
import uk.nickbdyer.httpserver.testdoubles.ControllerSpy;
import uk.nickbdyer.httpserver.testdoubles.SocketStubWithRequest;

import java.io.IOException;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static uk.nickbdyer.httpserver.requests.Method.*;

public class ControllerTest {

    @Test
    public void aControllerWillRespondToAGetRequest() {
        Request getRequest = new Request(GET, "/");
        ControllerSpy controller = new ControllerSpy();

        controller.execute(getRequest);

        assertEquals("get", controller.methodTriggered);
    }

    @Test
    public void aControllerWillRespondToAPostRequest() {
        Request getRequest = new Request(POST, "/");
        ControllerSpy controller = new ControllerSpy();

        controller.execute(getRequest);

        assertEquals("post", controller.methodTriggered);
    }

    @Test
    public void aControllerWillRespondToAPutRequest() {
        Request getRequest = new Request(PUT, "/");
        ControllerSpy controller = new ControllerSpy();

        controller.execute(getRequest);

        assertEquals("put", controller.methodTriggered);
    }

    @Test
    public void aControllerWillRespondToADeleteRequest() {
        Request getRequest = new Request(DELETE, "/");
        ControllerSpy controller = new ControllerSpy();

        controller.execute(getRequest);

        assertEquals("delete", controller.methodTriggered);
    }

    @Test
    public void aControllerWillRespondToAHeadRequest() {
        Request getRequest = new Request(HEAD, "/");
        ControllerSpy controller = new ControllerSpy();

        controller.execute(getRequest);

        assertEquals("head", controller.methodTriggered);
    }

    @Test
    public void aControllerWillRespondToAOptionsRequest() {
        Request getRequest = new Request(OPTIONS, "/");
        ControllerSpy controller = new ControllerSpy();

        controller.execute(getRequest);

        assertEquals("options", controller.methodTriggered);
    }

    @Test
    public void aControllerWillRespondToATraceRequest() {
        Request getRequest = new Request(TRACE, "/");
        ControllerSpy controller = new ControllerSpy();

        controller.execute(getRequest);

        assertEquals("trace", controller.methodTriggered);
    }

    @Test
    public void aControllerWillRespondToAConnectRequest() {
        Request getRequest = new Request(CONNECT, "/");
        ControllerSpy controller = new ControllerSpy();

        controller.execute(getRequest);

        assertEquals("connect", controller.methodTriggered);
    }

    @Test
    public void aControllerWillRespondToAnUnknownMethodWithAMethodNotAllowedResponse() throws IOException {
        SocketStubWithRequest socket = new SocketStubWithRequest("HELLO / HTTP/1.1");
        Request getRequest = new RequestParser(socket).parse();
        ControllerSpy controller = new ControllerSpy();

        Response response = controller.execute(getRequest);

        assertEquals("HTTP/1.1 405 Method Not Allowed\n", response.getStatusLine());
    }

    @Test
    public void aControllerWillRespondWithACorrectHeaderForAMethodNotAllowedResponse() throws IOException {
        SocketStubWithRequest socket = new SocketStubWithRequest("HELLO / HTTP/1.1");
        Request getRequest = new RequestParser(socket).parse();
        ControllerSpy controller = new ControllerSpy();

        Response response = controller.execute(getRequest);

        assertThat(response.getResponseHeader(), containsString("Allow: "));
        assertThat(response.getResponseHeader(), containsString("CONNECT,DELETE,GET,HEAD,OPTIONS,POST,PUT,TRACE"));
    }
}


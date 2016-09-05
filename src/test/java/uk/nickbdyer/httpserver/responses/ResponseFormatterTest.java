package uk.nickbdyer.httpserver.responses;

import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.httpserver.testdoubles.SocketStubWithOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResponseFormatterTest {

    private ByteArrayOutputStream receivedContent;
    private Socket socket;
    private ResponseFormatter dispatcher;

    @Before
    public void setUp() throws Exception {
        receivedContent = new ByteArrayOutputStream();
        socket = new SocketStubWithOutputStream(receivedContent);
    }

    @Test
    public void aResponseDispatcherCanSendAResponseWithoutABody() throws IOException {
        Response response = new Response(200, new HashMap<>(), "");
        dispatcher = new ResponseFormatter(socket, response);
        dispatcher.sendResponse();

        assertThat(receivedContent.toString(), containsString("HTTP/1.1 200 OK"));
    }

    @Test
    public void aResponseDispatcherCanSendAResponseWithBody() throws IOException {
        Response response = new Response(200, new HashMap<>(), "hello");
        dispatcher = new ResponseFormatter(socket, response);

        dispatcher.sendResponse();

        assertThat(receivedContent.toString(), containsString("hello"));
    }

    @Test
    public void aResponseWillShowAIncludeADateFieldInTheHeader() throws IOException {
        Response response = new Response(200, new HashMap<>(), "Body");
        dispatcher = new ResponseFormatter(socket, response);

        dispatcher.sendResponse();

        assertThat(receivedContent.toString(), containsString("Date: "));
    }

    @Test
    public void aResponseHeaderWillShowAContentLengthIfBodyIsPresent() throws IOException {
        Response response = new Response(200, new HashMap<>(), "Body");
        dispatcher = new ResponseFormatter(socket, response);

        dispatcher.sendResponse();

        assertThat(receivedContent.toString(), containsString("Content-Length: 4"));
    }
}

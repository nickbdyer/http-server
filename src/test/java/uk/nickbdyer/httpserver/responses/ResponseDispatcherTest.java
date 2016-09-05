package uk.nickbdyer.httpserver.responses;

import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.httpserver.testdoubles.SocketStubWithOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ResponseDispatcherTest {

    private ByteArrayOutputStream receivedContent;
    private Socket socket;
    private ResponseDispatcher dispatcher;

    @Before
    public void setUp() throws Exception {
        receivedContent = new ByteArrayOutputStream();
        socket = new SocketStubWithOutputStream(receivedContent);
        dispatcher = new ResponseDispatcher(socket);
    }

    @Test
    public void aResponseDispatcherCanSendAResponseWithoutABody() throws IOException {
        String statusLine = "HTTP/1.1 200 OK\n";

        dispatcher.sendResponse(statusLine, null);

        assertEquals("HTTP/1.1 200 OK\n", receivedContent.toString());
    }

    @Test
    public void aResponseDispatcherCanSendAResponseWithBody() throws IOException {
        String statusLine = "HTTP/1.1 200 OK\n";
        byte[] body = "hello".getBytes();

        dispatcher.sendResponse(statusLine, body);

        assertThat(receivedContent.toString(), containsString("hello"));
    }
}

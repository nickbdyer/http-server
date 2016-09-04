package uk.nickbdyer.httpserver.responses;

import org.junit.Test;
import uk.nickbdyer.httpserver.testdoubles.SocketStubWithOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class ResponseDispatcherTest {

    @Test
    public void aResponseDispatcherCanSendAResponse() throws IOException {
        ByteArrayOutputStream receivedContent = new ByteArrayOutputStream();
        Socket socket = new SocketStubWithOutputStream(receivedContent);

        ResponseDispatcher dispatcher = new ResponseDispatcher(socket);
        String statusLine = "HTTP/1.1 200 OK\n";
        byte[] body = null;
        dispatcher.sendResponse(statusLine, body);

        assertEquals("HTTP/1.1 200 OK\n", receivedContent.toString());
    }
}

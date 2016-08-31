package uk.nickbdyer.httpserver;

import org.junit.Test;
import uk.nickbdyer.httpserver.testdoubles.SocketStubWithOutputStream;
import uk.nickbdyer.httpserver.testdoubles.SocketStubWithRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class SocketHandlerTest {

    @Test
    public void aSocketHandlerCanExtractTheRequestFromASocket() throws IOException {
        Socket socket = new SocketStubWithRequest("GET / HTTP/1.1\n");

        SocketHandler socketHandler = new SocketHandler(socket);

        assertEquals("GET / HTTP/1.1", socketHandler.getRequest());
    }

    @Test
    public void aSocketHandlerCanSendAResponse() throws IOException {
        ByteArrayOutputStream receivedContent = new ByteArrayOutputStream();
        Socket socket = new SocketStubWithOutputStream(receivedContent);

        SocketHandler socketHandler = new SocketHandler(socket);
        String response = "HTTP/1.1 200 OK\n";
        socketHandler.sendResponse(response);

        assertEquals("HTTP/1.1 200 OK\n", receivedContent.toString());
    }


}
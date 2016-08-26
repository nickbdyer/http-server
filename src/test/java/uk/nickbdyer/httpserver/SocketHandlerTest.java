package uk.nickbdyer.httpserver;

import org.junit.Test;
import uk.nickbdyer.httpserver.testdoubles.SocketStubWithRequest;

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


}
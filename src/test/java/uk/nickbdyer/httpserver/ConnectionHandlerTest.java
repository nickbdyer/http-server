package uk.nickbdyer.httpserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertTrue;

public class ConnectionHandlerTest {

    private ServerSocket serverSocket;

    @Before
    public void setUp() throws IOException {
        serverSocket = new ServerSocket(5000);
    }

    @After
    public void tearDown() throws IOException {
        serverSocket.close();
    }

    @Test
    public void aConnectionHandlerWillReturnASocketWhenConnected() throws IOException {
        ConnectionHandler connectionHandler = new ConnectionHandler(serverSocket);

        new Socket("localhost", 5000);

        assertTrue(connectionHandler.getSocket() instanceof Socket);
    }
}

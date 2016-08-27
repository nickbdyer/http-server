package uk.nickbdyer.httpserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.httpserver.testdoubles.ConnectionHandlerSpy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertTrue;

public class HttpServerTest {

    private ServerSocket serverSocket;
    private ExecutorService executor;

    @Before
    public void setUp() throws IOException {
        serverSocket = new ServerSocket(5000);
        executor = Executors.newSingleThreadExecutor();
    }

    @After
    public void tearDown() throws IOException {
        serverSocket.close();
        executor.shutdownNow();
    }

    @Test
    public void theServerAllowsASocketConnectionToBeEstablished() throws IOException {
        ConnectionHandlerSpy connectionHandler = new ConnectionHandlerSpy(serverSocket);
        HttpServer server = new HttpServer(connectionHandler, "");

        executor.execute(server::listen);

        new Socket("localhost", 5000);

        assertTrue(connectionHandler.socketConnectionWasMade());
    }



}

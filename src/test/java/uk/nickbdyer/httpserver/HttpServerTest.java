package uk.nickbdyer.httpserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.httpserver.testdoubles.ConnectionHandlerSpy;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpServerTest {

    private ServerSocket serverSocket;
    private ExecutorService executor;
    private HttpServer server;
    private ConnectionHandlerSpy connectionHandler;

    @Before
    public void setUp() throws IOException {
        serverSocket = new ServerSocket(5000);
        executor = Executors.newSingleThreadExecutor();
        connectionHandler = new ConnectionHandlerSpy(serverSocket);
        server = new HttpServer(connectionHandler, new ResponseBuilder());
    }

    @After
    public void tearDown() throws IOException {
        serverSocket.close();
        executor.shutdownNow();
    }

    @Test
    public void theServerAllowsASocketConnectionToBeEstablished() throws IOException, InterruptedException {
        executor.execute(server::listen);

        new Socket("localhost", 5000);

        Thread.sleep(50);

        assertTrue(connectionHandler.socketConnectionWasMade());
    }

    @Test
    public void theServerAllowsMultipleSocketConnectionsToBeEstablished() throws IOException, InterruptedException {
        executor.execute(server::listen);

        connectSocketAndSendGetRequest();
        connectSocketAndSendGetRequest();
        connectSocketAndSendGetRequest();

        Thread.sleep(50);

        assertEquals(3, connectionHandler.numberOfConnectionsMade);
    }

    private void connectSocketAndSendGetRequest() throws IOException {
        Socket socket = new Socket("localhost", 5000);
        OutputStream output = socket.getOutputStream();
        output.write("GET / HTTP/1.1\n".getBytes());
        output.flush();
        output.close();
    }


}

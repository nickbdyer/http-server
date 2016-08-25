package uk.nickbdyer.httpserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpServerTest {

    private ExecutorService executor;
    private ServerSocket serverSocket;

    @Before
    public void setUp() throws IOException {
        executor = Executors.newSingleThreadExecutor();
        serverSocket = new ServerSocket(5000);
    }

    @After
    public void tearDown() throws IOException {
        serverSocket.close();
        executor.shutdownNow();
    }

    @Test
    public void aServerHasADirectoryPath() {
        HttpServer server = new HttpServer(executor, serverSocket, "/directory/path");

        assertEquals("/directory/path", server.getDirectoryPath());
    }

    @Test
    public void aListeningServerWillBindASocketConnection() throws IOException {
        HttpServer server = new HttpServer(executor, serverSocket, "");

        server.listen();
        Socket client = new Socket("localhost", 5000);

        assertTrue(client.isBound());
    }
    
    @Test
    public void aServerWillAcceptIncomingConnections() throws IOException {
        HttpServer server = new HttpServer(executor, serverSocket, "");

        server.listen();
        Socket client = new Socket("localhost", 5000);

        assertTrue(client.isConnected());
    }

    @Test
    public void serverWillRespond200OKToSimpleGET() throws IOException {
        HttpServer server = new HttpServer(executor, serverSocket, "");
        Socket client = new Socket("localhost", 5000);

        OutputStream request = client.getOutputStream();
        request.write("GET / HTTP/1.1\n".getBytes());

        server.listen();

        String response = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
        assertEquals("HTTP/1.1 200 OK", response);
    }

    @Test
    public void serverWillRespond404ToMissingRoute() throws IOException {
        HttpServer server = new HttpServer(executor, serverSocket, "");
        Socket client = new Socket("localhost", 5000);

        OutputStream request = client.getOutputStream();
        request.write("GET /foobar HTTP/1.1\n".getBytes());

        server.listen();

        String response = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
        assertEquals("HTTP/1.1 404 Not Found", response);
    }

}

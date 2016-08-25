package uk.nickbdyer.httpserver;

import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpServerTest {

    @Test
    public void aServerHasAPort() {
        HttpServer server = new HttpServer(5000, "");
        assertEquals(5000, server.getPort());
    }

    @Test
    public void aServerHasADirectoryPath() {
        HttpServer server = new HttpServer(5000, "/directory/path");

        assertEquals("/directory/path", server.getDirectoryPath());
    }

    @Test
    public void aListeningServerWillBindASocketConnection() throws IOException {
        HttpServer server = new HttpServer(5000, "");

        server.listen();
        Socket client = new Socket("localhost", 5000);

        assertTrue(client.isBound());

    }
}

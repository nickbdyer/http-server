package uk.nickbdyer.httpserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpServerTest {

    @Test
    public void aServerHasAPort() {
        HttpServer server = new HttpServer(5000, "/directory/path");
        assertEquals(5000, server.getPort());
    }

    @Test
    public void aServerHasADirectoryPath() {
        HttpServer server = new HttpServer(5000, "/directory/path");

        assertEquals("/directory/path", server.getDirectoryPath());
    }
}

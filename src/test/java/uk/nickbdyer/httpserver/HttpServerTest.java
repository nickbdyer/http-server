package uk.nickbdyer.httpserver;

import org.junit.Test;
import uk.nickbdyer.httpserver.testdoubles.ServerSocketSpy;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class HttpServerTest {

    @Test
    public void whenListeningTheNewSocketConnectionsWillBeAccepted() throws IOException {
        ServerSocketSpy socketSpy = new ServerSocketSpy();
        HttpServer server = new HttpServer(socketSpy, new Router());

        server.listen();

        assertTrue(socketSpy.acceptWasCalled());
    }
}

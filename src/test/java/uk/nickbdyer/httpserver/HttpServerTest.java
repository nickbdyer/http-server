package uk.nickbdyer.httpserver;

import org.junit.Test;
import uk.nickbdyer.httpserver.middleware.Logger;
import uk.nickbdyer.httpserver.testdoubles.DummyLogger;
import uk.nickbdyer.httpserver.testdoubles.ServerSocketSpy;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class HttpServerTest {

    @Test
    public void whenListeningTheNewSocketConnectionsWillBeAccepted() throws IOException {
        ServerSocketSpy socketSpy = new ServerSocketSpy();
        Logger logger = new DummyLogger();
        HttpServer server = new HttpServer(socketSpy, new Router(new File("")), logger);

        server.listen();

        assertTrue(socketSpy.acceptWasCalled());
    }
}

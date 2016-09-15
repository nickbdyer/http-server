package uk.nickbdyer.httpserver;

import org.junit.Ignore;
import org.junit.Test;
import uk.nickbdyer.httpserver.middleware.MiddlewareStack;
import uk.nickbdyer.httpserver.testdoubles.BrokenServerSocketStub;
import uk.nickbdyer.httpserver.testdoubles.ClosedServerSocketStub;
import uk.nickbdyer.httpserver.testdoubles.ServerSocketSpy;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertTrue;

public class HttpServerTest {

    @Test
    public void whenListeningTheNewSocketConnectionsWillBeAccepted() throws IOException {
        ServerSocketSpy socketSpy = new ServerSocketSpy();
        MiddlewareStack middlewareStack = new MiddlewareStack();
        HttpServer server = new HttpServer(Executors.newSingleThreadExecutor(), socketSpy, middlewareStack);

        server.listen();

        assertTrue(socketSpy.acceptWasCalled());
    }

    @Ignore
    @Test
    public void serverWillCatchSocketClosedExceptions() throws IOException {
        ClosedServerSocketStub socketStub = new ClosedServerSocketStub();
        MiddlewareStack middlewareStack = new MiddlewareStack();
        HttpServer server = new HttpServer(Executors.newSingleThreadExecutor(), socketStub, middlewareStack);

        server.listen();

//        assertTrue(logger.logs().contains("Server shutdown..."));
    }

    @Test(expected = UncheckedIOException.class)
    public void serverWillThrowUncheckedIOExceptionsIfNecessary() throws IOException {
        BrokenServerSocketStub socketStub = new BrokenServerSocketStub();
        MiddlewareStack middlewareStack = new MiddlewareStack();
        HttpServer server = new HttpServer(Executors.newSingleThreadExecutor(), socketStub, middlewareStack);

        server.listen();

    }
}

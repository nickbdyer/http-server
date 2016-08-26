package uk.nickbdyer.httpserver;

import org.junit.Test;
import uk.nickbdyer.httpserver.testdoubles.ServerSocketSpy;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class HttpServerTest {

    @Test
    public void theServerCanAcceptAConnection() throws IOException {
        ServerSocketSpy socketSpy = new ServerSocketSpy(5000);
        HttpServer server = new HttpServer(socketSpy, "");

        server.listen();

        assertTrue(socketSpy.acceptWasCalled());
    }

//    Need a way to test multiple incoming connections.
//    Cannot override accept since this is tied to SocketImpl which is difficult to work with.


}

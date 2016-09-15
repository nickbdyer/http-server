package uk.nickbdyer.httpserver;

import org.junit.Test;
import uk.nickbdyer.httpserver.middleware.MiddlewareStack;
import uk.nickbdyer.httpserver.testdoubles.BrokenInputStreamSocket;
import uk.nickbdyer.httpserver.testdoubles.SocketStub;
import uk.nickbdyer.httpserver.testdoubles.UnreadableSocketStub;

import java.io.ByteArrayOutputStream;
import java.io.UncheckedIOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class SocketHandlerTest {

    @Test
    public void socketHandlerWillRespondToARequest() {
        ByteArrayOutputStream recievedResponse = new ByteArrayOutputStream();
        SocketStub socketStub = new SocketStub("GET / HTTP/1.1", recievedResponse);
        MiddlewareStack middlewareStack = new MiddlewareStack();
        SocketHandler socketHandler = new SocketHandler(socketStub, middlewareStack);

        socketHandler.processRequestAndRespond();

        assertThat(recievedResponse.toString(), containsString("HTTP/1.1 404 Not Found"));
    }

    @Test(expected = UncheckedIOException.class)
    public void socketHandlerWillThrowUncheckIOExceptionIfStreamsCannotBeRead() {
        MiddlewareStack middlewareStack = new MiddlewareStack();
        SocketHandler socketHandler = new SocketHandler(new UnreadableSocketStub(), middlewareStack);

        socketHandler.processRequestAndRespond();
    }

    @Test
    public void socketHandlerWillReturnA500IfPossible() {
        ByteArrayOutputStream receivedContent = new ByteArrayOutputStream();
        MiddlewareStack middlewareStack = new MiddlewareStack();
        SocketHandler socketHandler = new SocketHandler(new BrokenInputStreamSocket(receivedContent), middlewareStack);

        socketHandler.processRequestAndRespond();

        assertThat(receivedContent.toString(), containsString("HTTP/1.1 500 Internal Server Error"));
    }
}

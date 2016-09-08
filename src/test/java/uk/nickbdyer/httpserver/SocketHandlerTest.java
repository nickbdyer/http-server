package uk.nickbdyer.httpserver;

import org.junit.Test;
import uk.nickbdyer.httpserver.testdoubles.DummyLogger;
import uk.nickbdyer.httpserver.testdoubles.SocketStub;
import uk.nickbdyer.httpserver.testdoubles.UnreadableSocketStub;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UncheckedIOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class SocketHandlerTest {

    @Test
    public void socketHandlerWillRespondToARequest() {
        ByteArrayOutputStream recievedResponse = new ByteArrayOutputStream();
        SocketStub socketStub = new SocketStub("GET / HTTP/1.1", recievedResponse);
        SocketHandler socketHandler = new SocketHandler(socketStub, new DummyLogger(), new Router(new File("")));

        socketHandler.processRequestAndRespond();

        assertThat(recievedResponse.toString(), containsString("HTTP/1.1 404 Not Found"));
    }

    @Test(expected = UncheckedIOException.class)
    public void socketHandlerWillThrowUncheckIOExceptionIfStreamsCannotBeRead() {
        SocketHandler socketHandler = new SocketHandler(new UnreadableSocketStub(), new DummyLogger(), new Router(new File("")));

        socketHandler.processRequestAndRespond();
    }
}

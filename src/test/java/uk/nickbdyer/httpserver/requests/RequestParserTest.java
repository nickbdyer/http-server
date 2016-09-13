package uk.nickbdyer.httpserver.requests;

import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.httpserver.testdoubles.DummyLogger;
import uk.nickbdyer.httpserver.testdoubles.SocketStubWithRequest;
import uk.nickbdyer.httpserver.testdoubles.UnreadableSocketStub;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;
import static uk.nickbdyer.httpserver.requests.Method.*;

public class RequestParserTest {

    private DummyLogger logger;

    @Before
    public void setUp() {
        logger = new DummyLogger();
    }

    @Test
    public void requestParserRecognisesGETMethod() throws IOException {
        Socket socket = new SocketStubWithRequest("GET / HTTP/1.1\nHost: localhost:5000\r\n");

        Request request = new RequestParser(socket, logger).parse();

        assertEquals(GET, request.getMethod());
    }

    @Test
    public void requestParserRecognisesPOSTMethod() throws IOException {
        Socket socket = new SocketStubWithRequest("POST / HTTP/1.1\nHost: localhost:5000\r\n");

        Request request = new RequestParser(socket, logger).parse();

        assertEquals(POST, request.getMethod());
    }

    @Test
    public void requestParserRecognisesHEADMethod() throws IOException {
        Socket socket = new SocketStubWithRequest("HEAD / HTTP/1.1\nHost: localhost:5000\r\n");

        Request request = new RequestParser(socket, logger).parse();

        assertEquals(HEAD, request.getMethod());
    }

    @Test
    public void requestParserWillReturnUnknownMethodForNonStandardMethods() throws IOException {
        Socket socket = new SocketStubWithRequest("HELLO / HTTP/1.1\nHost: localhost:5000\r\n");

        Request request = new RequestParser(socket, logger).parse();

        assertEquals(UNKNOWN_METHOD, request.getMethod());
    }

    @Test
    public void requestParserCanExtractThePath() throws IOException {
        Socket socket = new SocketStubWithRequest("HEAD / HTTP/1.1\nHost: localhost:5000\r\n");

        Request request = new RequestParser(socket, logger).parse();

        assertEquals("/", request.getPath());
    }

    @Test
    public void requestParserCanExtractAnotherPath() throws IOException {
        Socket socket = new SocketStubWithRequest("GET /foobar HTTP/1.1\nHost: localhost:5000\r\n");

        Request request = new RequestParser(socket, logger).parse();

        assertEquals("/foobar", request.getPath());
    }

    @Test
    public void requestParserWillParseRequestWithoutHeaders() throws IOException {
        Socket socket = new SocketStubWithRequest("GET /foobar HTTP/1.1\n\r\n\r\n");

        Request request = new RequestParser(socket, logger).parse();

        assertEquals("/foobar", request.getPath());
    }

    @Test
    public void requestParserWillReturnNoHeadersIfNoneAreParsed() throws IOException {
        Socket socket = new SocketStubWithRequest("GET /foobar HTTP/1.1\n\r\n\r\n");

        Request request = new RequestParser(socket, logger).parse();

        assertEquals(0, request.getHeaders().size());
    }

    @Test
    public void requestParserCanExtractHeaders() throws IOException {
        String requestString = "GET /foobar HTTP/1.1\n" +
                "Content-Length: 11\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate\n";
        Socket socket = new SocketStubWithRequest(requestString);

        Request request = new RequestParser(socket, logger).parse();

        assertEquals(5, request.getHeaders().size());
    }

    @Test
    public void requestParserWillIgnoreBodyWhenParsingHeaders() throws IOException {
        String requestString = "GET /foobar HTTP/1.1\n" +
                "Content-Length: 11\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate" +
                "\r\n\r\n" +
                "Body: That Looks Like A Header";
        Socket socket = new SocketStubWithRequest(requestString);

        Request request = new RequestParser(socket, logger).parse();

        assertFalse(request.getHeaders().containsKey("Body"));
    }

    @Test
    public void requestParserWillParseABodyIfItExists() throws IOException {
        String requestString = "GET /foobar HTTP/1.1\n" +
                "Content-Length: 11\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate" +
                "\r\n\r\n" +
                "data=fatcat";
        Socket socket = new SocketStubWithRequest(requestString);

        Request request = new RequestParser(socket, logger).parse();

        assertEquals("data=fatcat", request.getBody());
    }

    @Test
    public void requestParserWillParseQueryParametersIfTheyExist() throws IOException {
        String requestString = "GET /foobar?hello=goodbye HTTP/1.1\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate\r\n\r\n";
        Socket socket = new SocketStubWithRequest(requestString);

        Request request = new RequestParser(socket, logger).parse();

        assertEquals("/foobar", request.getPath());
        assertTrue(request.getParameters().containsKey("hello"));
        assertEquals("goodbye", request.getParameters().get("hello"));
    }

    @Test
    public void requestParserWillPassANullMethodIfTheRequestCannotBeRead() throws IOException {
        Socket socket = new UnreadableSocketStub();

        Request request = new RequestParser(socket, logger).parse();

        assertEquals(null, request.getMethod());
    }

}

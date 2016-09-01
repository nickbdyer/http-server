package uk.nickbdyer.httpserver;

import org.junit.Test;
import uk.nickbdyer.httpserver.testdoubles.SocketStubWithRequest;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static uk.nickbdyer.httpserver.Method.*;

public class RequestParserTest {

    @Test
    public void requestParserRecognisesGETMethod() throws IOException {
        Socket socket = new SocketStubWithRequest("GET / HTTP/1.1\nHost: localhost:5000\r\n");
        RequestParser parser = new RequestParser(socket);

        Request request = parser.parse();

        assertEquals(GET, request.getMethod());
    }

    @Test
    public void requestParserRecognisesPOSTMethod() throws IOException {
        Socket socket = new SocketStubWithRequest("POST / HTTP/1.1\nHost: localhost:5000\r\n");
        RequestParser parser = new RequestParser(socket);

        Request request = parser.parse();

        assertEquals(POST, request.getMethod());
    }

    @Test
    public void requestParserRecognisesHEADMethod() throws IOException {
        Socket socket = new SocketStubWithRequest("HEAD / HTTP/1.1\nHost: localhost:5000\r\n");
        RequestParser parser = new RequestParser(socket);

        Request request = parser.parse();

        assertEquals(HEAD, request.getMethod());
    }

    @Test
    public void requestParserWillReturnINVALIDMETHOD() throws IOException {
        Socket socket = new SocketStubWithRequest("HELLO / HTTP/1.1\nHost: localhost:5000\r\n");
        RequestParser parser = new RequestParser(socket);

        Request request = parser.parse();

        assertEquals(INVALID_METHOD, request.getMethod());
    }

    @Test
    public void requestParserCanExtractTheRoute() throws IOException {
        Socket socket = new SocketStubWithRequest("HEAD / HTTP/1.1\nHost: localhost:5000\r\n");
        RequestParser parser = new RequestParser(socket);

        Request request = parser.parse();

        assertEquals("/", request.getRoute());
    }

    @Test
    public void requestParserCanExtractAnotherRoute() throws IOException {
        Socket socket = new SocketStubWithRequest("GET /foobar HTTP/1.1\nHost: localhost:5000\r\n");
        RequestParser parser = new RequestParser(socket);

        Request request = parser.parse();

        assertEquals("/foobar", request.getRoute());
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
        RequestParser parser = new RequestParser(socket);

        Request request = parser.parse();

        assertEquals(5, request.getHeaders().size());
    }
    
    @Test
    public void requestParserWillIgnoreBodyWhenParsingHeaders() throws IOException {
        String CRLF = String.valueOf((char) 13) + (char) 10;
        String requestString = "GET /foobar HTTP/1.1\n" +
                "Content-Length: 11\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate" +
                CRLF + CRLF +
                "Body: That Looks Like A Header\n";

        Socket socket = new SocketStubWithRequest(requestString);
        RequestParser parser = new RequestParser(socket);

        Request request = parser.parse();

        assertFalse(request.getHeaders().containsKey("Body"));
    }

    @Test
    public void requestParserWillParseABodyIfItExists() throws IOException {
        String CRLF = String.valueOf((char) 13) + (char) 10;
        String requestString = "GET /foobar HTTP/1.1\n" +
                "Content-Length: 11\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate" +
                CRLF + CRLF +
                "data=fatcat\n";

        Socket socket = new SocketStubWithRequest(requestString);
        RequestParser parser = new RequestParser(socket);

        Request request = parser.parse();

        assertEquals("data=fatcat", request.getBody());
    }


}

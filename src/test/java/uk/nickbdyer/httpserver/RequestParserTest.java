package uk.nickbdyer.httpserver;

import org.junit.Test;
import uk.nickbdyer.httpserver.testdoubles.SocketStubWithOutputStream;
import uk.nickbdyer.httpserver.testdoubles.SocketStubWithRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.Method.*;

public class RequestParserTest {

    @Test
    public void requestParserRecognisesGETMethod() throws IOException {
        Socket socket = new SocketStubWithRequest("GET / HTTP/1.1\n");
        RequestParser parser = new RequestParser(socket);

        Request request = parser.parse();

        assertEquals(GET, request.getMethod());
    }

    @Test
    public void requestParserRecognisesPOSTMethod() throws IOException {
        Socket socket = new SocketStubWithRequest("POST / HTTP/1.1\n");
        RequestParser parser = new RequestParser(socket);

        Request request = parser.parse();

        assertEquals(POST, request.getMethod());
    }

    @Test
    public void requestParserRecognisesHEADMethod() throws IOException {
        Socket socket = new SocketStubWithRequest("HEAD / HTTP/1.1\n");
        RequestParser parser = new RequestParser(socket);

        Request request = parser.parse();

        assertEquals(HEAD, request.getMethod());
    }

    @Test
    public void requestParserWillReturnINVALIDMETHOD() throws IOException {
        Socket socket = new SocketStubWithRequest("HELLO / HTTP/1.1\n");
        RequestParser parser = new RequestParser(socket);

        Request request = parser.parse();

        assertEquals(METHOD_NOT_ALLOWED, request.getMethod());
    }

    @Test
    public void requestParserCanExtractTheRoute() throws IOException {
        Socket socket = new SocketStubWithRequest("HEAD / HTTP/1.1\n");
        RequestParser parser = new RequestParser(socket);

        Request request = parser.parse();

        assertEquals("/", request.getRoute());
    }

    @Test
    public void requestParserCanExtractAnotherRoute() throws IOException {
        Socket socket = new SocketStubWithRequest("GET /foobar HTTP/1.1\n");
        RequestParser parser = new RequestParser(socket);

        Request request = parser.parse();

        assertEquals("/foobar", request.getRoute());
    }

    @Test
    public void aSocketHandlerCanSendAResponse() throws IOException {
        ByteArrayOutputStream receivedContent = new ByteArrayOutputStream();
        Socket socket = new SocketStubWithOutputStream(receivedContent);

        RequestParser parser = new RequestParser(socket);
        String response = "HTTP/1.1 200 OK\n";
        parser.sendResponse(response);

        assertEquals("HTTP/1.1 200 OK\n", receivedContent.toString());
    }

//    @Test
//    public void requestParserCanExtractHeaders() {
//        String requestString = "GET /foobar HTTP/1.1\n" +
//                "Content-Length: 11\n" +
//                "Host: localhost:5000\n" +
//                "Connection: Keep-Alive\n" +
//                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
//                "Accept-Encoding: gzip,deflate\n";
//
//        Request request = parser.parse(requestString);
//
//        assertEquals(5, request.getHeaders().keys().length());
//    }

}

package uk.nickbdyer.httpserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.Method.*;

public class RequestParserTest {

    @Test
    public void requestParserRecognisesGETMethod() {
        String requestString = "GET / HTTP/1.1\n";
        RequestParser parser = new RequestParser();

        Request request = parser.parse(requestString);

        assertEquals(GET, request.getMethod());
    }

    @Test
    public void requestParserRecognisesPOSTMethod() {
        String requestString = "POST / HTTP/1.1\n";
        RequestParser parser = new RequestParser();

        Request request = parser.parse(requestString);

        assertEquals(POST, request.getMethod());
    }

    @Test
    public void requestParserRecognisesHEADMethod() {
        String requestString = "HEAD / HTTP/1.1\n";
        RequestParser parser = new RequestParser();

        Request request = parser.parse(requestString);

        assertEquals(HEAD, request.getMethod());
    }

    @Test
    public void requestParserCanExtractTheRoute() {
        String requestString = "HEAD / HTTP/1.1\n";
        RequestParser parser = new RequestParser();

        Request request = parser.parse(requestString);

        assertEquals("/", request.getRoute());
    }

    @Test
    public void requestParserCanExtractAnotherRoute() {
        String requestString = "GET /foobar HTTP/1.1\n";
        RequestParser parser = new RequestParser();

        Request request = parser.parse(requestString);

        assertEquals("/foobar", request.getRoute());
    }
}

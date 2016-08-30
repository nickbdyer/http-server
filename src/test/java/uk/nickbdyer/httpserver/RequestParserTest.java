package uk.nickbdyer.httpserver;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static uk.nickbdyer.httpserver.Method.*;
import static uk.nickbdyer.httpserver.Response.*;

public class RequestParserTest {

    private RequestParser parser;

    @Before
    public void setUp() {
        parser = new RequestParser();
    }

    @Test
    public void requestParserRecognisesGETMethod() {
        String requestString = "GET / HTTP/1.1\n";

        Request request = parser.parse(requestString);

        assertEquals(GET, request.getMethod());
    }

    @Test
    public void requestParserRecognisesPOSTMethod() {
        String requestString = "POST / HTTP/1.1\n";

        Request request = parser.parse(requestString);

        assertEquals(POST, request.getMethod());
    }

    @Test
    public void requestParserRecognisesHEADMethod() {
        String requestString = "HEAD / HTTP/1.1\n";

        Request request = parser.parse(requestString);

        assertEquals(HEAD, request.getMethod());
    }

    @Test
    public void requestParserWillReturnINVALIDMETHOD() {
        String requestString = "HELLO / HTTP/1.1\n";

        Request request = parser.parse(requestString);

        assertEquals(METHOD_NOT_ALLOWED, request.getMethod());
    }

    @Test
    public void requestParserCanExtractTheRoute() {
        String requestString = "HEAD / HTTP/1.1\n";

        Request request = parser.parse(requestString);

        assertEquals("/", request.getRoute());
    }

    @Test
    public void requestParserCanExtractAnotherRoute() {
        String requestString = "GET /foobar HTTP/1.1\n";

        Request request = parser.parse(requestString);

        assertEquals("/foobar", request.getRoute());
    }

    @Test
    public void requestParserWillReturnADefinedResponseIfItExists() {
        Request request = new Request(GET, "/").thatRespondsWith(OK());
        RequestParser requestParser = new RequestParser();
        requestParser.add(request);

        assertEquals(OK(), requestParser.getResponse(requestParser.parse("GET / HTTP/1.1")));
    }

    @Test
    public void requestParserWillReturnANotFoundResponseIfNoDefinedResponseIsFound() {
        Request request = new Request(GET, "/");
        RequestParser requestParser = new RequestParser();
        requestParser.add(request);

        assertEquals(NotFound(), requestParser.getResponse(requestParser.parse("GET / HTTP/1.1")));
    }

    @Test
    public void requestParserWillReturnANotFoundResponseIfRequestIsNotValid() {
        RequestParser requestParser = new RequestParser();

        assertEquals(NotFound(), requestParser.getResponse(requestParser.parse("GET / HTTP/1.1")));
    }
}

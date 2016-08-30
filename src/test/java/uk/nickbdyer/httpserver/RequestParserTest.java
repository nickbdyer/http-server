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
        parser = new RequestParser(new ArrayList<>());
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

        assertEquals(INVALID_METHOD, request.getMethod());
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
    public void requestParserKnowsIfARequestIsValid() {
        Request request = new Request(GET, "/");
        List<Request> validRequests = new ArrayList<>();
        validRequests.add(request);

        RequestParser requestParser = new RequestParser(validRequests);

        assertTrue(requestParser.isValid(requestParser.parse("GET / HTTP/1.1")));
    }

    @Test
    public void requestParserKnowsIfARequestIsInvalid() {
        RequestParser requestParser = new RequestParser(new ArrayList<>());

        assertFalse(requestParser.isValid(requestParser.parse("GET / HTTP/1.1")));
    }

    @Test
    public void requestParserWillReturnADefinedResponseIfItExists() {
        Request request = new Request(GET, "/").thatRespondsWith(OK());
        List<Request> validRequests = new ArrayList<>();
        validRequests.add(request);

        RequestParser requestParser = new RequestParser(validRequests);

        assertEquals(OK(), requestParser.getResponse(requestParser.parse("GET / HTTP/1.1")));
    }

    @Test
    public void requestParserWillReturnANotFoundResponseIfNoDefinedResponseIsFound() {
        Request request = new Request(GET, "/");
        List<Request> validRequests = new ArrayList<>();
        validRequests.add(request);

        RequestParser requestParser = new RequestParser(validRequests);

        assertEquals(NotFound(), requestParser.getResponse(requestParser.parse("GET / HTTP/1.1")));
    }

    @Test
    public void requestParserWillReturnANotFoundResponseIfRequestIsNotValid() {
        List<Request> validRequests = new ArrayList<>();

        RequestParser requestParser = new RequestParser(validRequests);

        assertEquals(NotFound(), requestParser.getResponse(requestParser.parse("GET / HTTP/1.1")));
    }
}

package uk.nickbdyer.httpserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {

    @Test
    public void requestParserRecognisesGETMethods() {
        String requestString = "GET / HTTP/1.1\n";
        RequestParser parser = new RequestParser();

        Request request = parser.parse(requestString);

        assertEquals(Method.GET, request.getMethod());
    }
}

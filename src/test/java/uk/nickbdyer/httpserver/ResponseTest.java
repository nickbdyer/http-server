package uk.nickbdyer.httpserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseTest {

    @Test
    public void anOKResponseHasTheCorrectStatusLine() {
        Response response = Response.OK();

        assertEquals("HTTP/1.1 200 OK", response.getStatusLine());
    }
}

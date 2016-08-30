package uk.nickbdyer.httpserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseTest {

    @Test
    public void anOKResponseHasTheCorrectStatusLine() {
        Response response = Response.OK();

        assertEquals("HTTP/1.1 200 OK", response.getStatusLine());
    }

    @Test
    public void anNotFoundResponseHasTheCorrectStatusLine() {
        Response response = Response.NotFound();

        assertEquals("HTTP/1.1 404 Not Found", response.getStatusLine());
    }

    @Test
    public void anRedirectResponseHasTheCorrectStatusLine() {
        Response response = Response.Redirect();

        assertEquals("HTTP/1.1 302 Found", response.getStatusLine());
    }
}

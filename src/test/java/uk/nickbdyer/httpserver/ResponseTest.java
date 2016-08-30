package uk.nickbdyer.httpserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseTest {

    @Test
    public void anOKResponseHasTheCorrectStatusLine() {
        Response response = Response.OK();

        assertEquals("HTTP/1.1 200 OK\n", response.getStatusLine());
    }

    @Test
    public void aNotFoundResponseHasTheCorrectStatusLine() {
        Response response = Response.NotFound();

        assertEquals("HTTP/1.1 404 Not Found\n", response.getStatusLine());
    }

    @Test
    public void aRedirectResponseCanAlsoHaveADefinedLocation() {
        Response response = Response.Redirect("another location");

        assertEquals("Location: another location\n", response.getResponseHeader());
    }

}

package uk.nickbdyer.httpserver.responses;

import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class ResponseTest {

    @Test
    public void aResponseWillHaveAStatusLine() {
        Response response = new Response(200, new HashMap<>(), "Body");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void aResponseWillShowABodyIfItIsPresent() {
        Response response = new Response(200, new HashMap<>(), "Body");

        assertThat(new String(response.getBody()), containsString("Body"));
    }

    @Test
    public void aResponseHeaderWillNotShowAContentLengthIfBodyIsNotPresent() {
        Response response = new Response(200, new HashMap<>(), "");

        assertFalse(response.getHeaders().containsKey("Content-Length: "));
    }

    @Test
    public void aResponseBodyWillBeNullIfNotNoBodyIsPresent() {
        Response response = new Response(200, new HashMap<>(), "");

        assertEquals(0, response.getBody().length);
    }

}

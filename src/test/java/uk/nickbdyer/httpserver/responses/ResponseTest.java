package uk.nickbdyer.httpserver.responses;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
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

        assertEquals(null, response.getBody());
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void aResponseHeaderWillTheContentTypeForATextFile() throws IOException {
        File file = folder.newFile("testfile.txt");
        Response response = new Response(200, new HashMap<>(),  file);

        assertTrue(response.getHeaders().containsKey("Content-Type: "));
        assertEquals("text/plain\n", response.getHeaders().get("Content-Type: "));
    }

    @Test
    public void aResponseHeaderWillTheContentTypeForAnImageFile() throws IOException {
        File file = folder.newFile("testfile.png");
        Response response = new Response(200, new HashMap<>(), file);

        assertTrue(response.getHeaders().containsKey("Content-Type: "));
        assertEquals("image/png\n", response.getHeaders().get("Content-Type: "));
    }

}

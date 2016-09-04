package uk.nickbdyer.httpserver.responses;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ResponseTest {

    @Test
    public void aResponseWillHaveAStatusLine() {
        Response response = new Response("Status-Line", "Headers", "Body");

        assertEquals("Status-Line\n", response.getStatusLine());
    }

    @Test
    public void aResponseWillHaveANewLineBetweenHeadersAndBody() {
        Response response = new Response("Status-Line", "Headers", "Body");

        assertThat(response.getHeader(), containsString("\r\n\r\n"));
    }

    @Test
    public void aResponseWillShowAIncludeADateFieldInTheHeader() {
        Response response = new Response("Status-Line", "Headers", "Body");

        assertThat(response.getHeader(), containsString("Date:"));
    }

    @Test
    public void aResponseWillShowABodyIfItIsPresent() {
        Response response = new Response("Status-Line", "Headers", "Body");

        assertThat(response.getResponse(), containsString("Body"));
    }

    @Test
    public void aResponseHeaderWillShowAContentLengthIfBodyIsPresent() {
        Response response = new Response("Status-Line", "Headers", "Body");

        assertThat(response.getResponse(), containsString("Content-Length: 4"));
    }

    @Test
    public void aResponseHeaderWillNotShowAContentLengthIfBodyIsNotPresent() {
        Response response = new Response("Status-Line", "Headers", null);

        assertThat(response.getResponse(), not(containsString("Content-Length: ")));
    }

    @Test
    public void aResponseBodyWillNotShowNullIfBodyIsNotPresent() {
        Response response = new Response("Status-Line", "Headers", null);

        assertThat(response.getResponse(), not(containsString("null")));
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void aResponseHeaderWillTheContentTypeForATextFile() throws IOException {
        File file = folder.newFile("testfile.txt");
        Response response = new Response("Status-Line", file);

        assertThat(response.getHeader(), containsString("Content-Type: text/plain"));
    }

    @Test
    public void aResponseHeaderWillTheContentTypeForAnImageFile() throws IOException {
        File file = folder.newFile("testfile.png");
        Response response = new Response("Status-Line", file);

        assertThat(response.getHeader(), containsString("Content-Type: image/png"));
    }

}

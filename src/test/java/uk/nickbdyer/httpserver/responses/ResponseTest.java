package uk.nickbdyer.httpserver.responses;

import org.junit.Test;

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

        assertThat(response.getResponseHeader(), containsString("\r\n\r\n"));
    }

    @Test
    public void aResponseWillShowAIncludeADateFieldInTheHeader() {
        Response response = new Response("Status-Line", "Headers", "Body");

        assertThat(response.getResponseHeader(), containsString("Date:"));
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

}

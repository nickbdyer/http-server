package uk.nickbdyer.httpserver;

import org.junit.Test;
import uk.nickbdyer.httpserver.Responses.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static uk.nickbdyer.httpserver.Method.*;

public class ResponseTest {

    @Test
    public void anOKResponseHasTheCorrectStatusLine() {
        Response response = new OK();

        assertThat(response.getResponse(), containsString("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void aNotFoundResponseHasTheCorrectStatusLine() {
        Response response = new NotFound();

        assertThat(response.getResponse(), containsString("HTTP/1.1 404 Not Found\n"));
    }

    @Test
    public void aRedirectResponseCanAlsoHaveADefinedLocation() {
        Response response = new Redirect("another location");

        assertThat(response.getResponse(), containsString("Location: another location"));
    }

    @Test
    public void aMethodNotAllowedResponseCanListAllowedMethods() {
        Response response = new MethodNotAllowed(new ArrayList<>(Arrays.asList(GET, POST, HEAD)));

        assertThat(response.getResponse(), containsString("Allow: GET, POST, HEAD\n"));
    }

}

package uk.nickbdyer.httpserver;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.Method.*;

public class ConcreteResponseTest {

    @Test
    public void anOKResponseHasTheCorrectStatusLine() {
        ConcreteResponse response = ConcreteResponse.OK();

        assertEquals("HTTP/1.1 200 OK\n", response.getStatusLine());
    }

    @Test
    public void aNotFoundResponseHasTheCorrectStatusLine() {
        ConcreteResponse response = ConcreteResponse.NotFound();

        assertEquals("HTTP/1.1 404 Not Found\n", response.getStatusLine());
    }

    @Test
    public void aRedirectResponseCanAlsoHaveADefinedLocation() {
        ConcreteResponse response = ConcreteResponse.Redirect("another location");

        assertEquals("Location: another location\n", response.getResponseHeader());
    }

    @Test
    public void aMethodNotAllowedResponseCanListAllowedMethods() {
        ConcreteResponse response = ConcreteResponse.MethodNotAllowed(new ArrayList<>(Arrays.asList(GET, POST, HEAD)));

        assertEquals("Allow: GET, POST, HEAD\n", response.getResponseHeader());
    }

}

package uk.nickbdyer.httpserver.middleware;

import org.junit.Test;

import java.util.Base64;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BasicAuthTest {

    @Test
    public void basicAuthCanAuthoriseAUser() {
        BasicAuth authorisor = new BasicAuth();
        String encodedUser = Base64.getEncoder().encodeToString(("admin" + ":" + "password").getBytes());

        authorisor.addAuthorisedUser("admin", "password");

        assertTrue(authorisor.userIsAuthorised(encodedUser));
    }

    @Test
    public void basicAuthProvideAnUnAuthorisedHeader() {
        BasicAuth authorisor = new BasicAuth();

        Map<String, String> header = authorisor.getUnAuthorisedHeader();

        assertTrue(header.containsKey("WWW-Authenticate"));
        assertEquals("Basic realm=\"NicksServer\"", header.get("WWW-Authenticate"));
    }
}

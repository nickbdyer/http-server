package uk.nickbdyer.httpserver.middleware;

import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.assertTrue;

public class BasicAuthTest {

    @Test
    public void basicAuthCanAuthoriseAUser() {
        BasicAuth authorisor = new BasicAuth();
        String encodedUser = Base64.getEncoder().encodeToString(("admin" + ":" + "password").getBytes());

        authorisor.addAuthorisedUser("admin", "password");

        assertTrue(authorisor.userIsAuthorised(encodedUser));
    }
}

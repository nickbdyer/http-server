package uk.nickbdyer.httpserver.middleware;

import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.requests.RequestLine;
import uk.nickbdyer.httpserver.responses.Response;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static uk.nickbdyer.httpserver.requests.Method.GET;

public class BasicAuthTest {

    @Test
    public void basicAuthCanAuthoriseAUser() {
        BasicAuth authorisor = new BasicAuth();
        String encodedUser = Base64.getEncoder().encodeToString(("admin" + ":" + "password").getBytes());

        authorisor.addAuthorisedUser("/logs", "admin", "password");

        assertTrue(authorisor.userIsAuthorised("/logs", encodedUser));
    }

    @Test
    public void basicAuthProvideAnUnAuthorisedHeader() {
        BasicAuth authorisor = new BasicAuth();

        Map<String, String> header = authorisor.getUnAuthorisedHeader();

        assertTrue(header.containsKey("WWW-Authenticate"));
        assertEquals("Basic realm=\"NicksServer\"", header.get("WWW-Authenticate"));
    }

    @Test
    public void authWillReturn401ForUnauthorisedRequest() {
        BasicAuth auth = new BasicAuth();
        auth.addAuthorisedUser("/auth", "admin", "password");
        RequestLine requestLine = new RequestLine(GET, "/auth", new HashMap<>());
        Request request = new Request(requestLine, new HashMap<>(), "");

        Response response = auth.call(request);

        assertEquals(401, response.getStatusCode());
    }

    @Test
    public void authWillReturn200ForAuthorisedRequest() {
        BasicAuth auth = new BasicAuth();
        auth.setNext(new OkMiddleware());
        auth.addAuthorisedUser("/auth", "admin", "password");
        RequestLine requestLine = new RequestLine(GET, "/auth", new HashMap<>());
        Request request = new Request(requestLine, headerWithEncodedUserAndPassword("admin", "password"), "");

        Response response = auth.call(request);

        assertEquals(200, response.getStatusCode());
    }

    class OkMiddleware extends Middleware {
        @Override
        public Response call(Request request) {
            return new Response(200, new HashMap<>(), "");
        }
    }

    private Map<String, String> headerWithEncodedUserAndPassword(String user, String pass) {
        String encodedAuth = Base64.getEncoder().encodeToString((user + ":" + pass).getBytes());
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", ("Basic " + encodedAuth));
        return headers;
    }

}

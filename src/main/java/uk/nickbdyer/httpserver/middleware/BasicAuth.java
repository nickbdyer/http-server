package uk.nickbdyer.httpserver.middleware;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class BasicAuth extends Middleware {

    private final ArrayList<String> authorisedUsers;

    @Override
    public Response call(Request request) {
        String user = request.getHeaders().getOrDefault("Authorization", "");
        if (userIsAuthorised(user)) {
            return next.call(request);
        }
        return new Response(401, getUnAuthorisedHeader(), "");
    }

    public BasicAuth() {
        super();
        this.authorisedUsers = new ArrayList<>();
    }

    public void addAuthorisedUser(String username, String password) {
        authorisedUsers.add(encodeUser(username, password));
    }

    private String encodeUser(String username, String password) {
        return Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    public boolean userIsAuthorised(String digest) {
        String digestWithoutType = digest.substring(digest.indexOf(' ') + 1);
        return authorisedUsers.contains(digestWithoutType);
    }

    public Map<String, String> getUnAuthorisedHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("WWW-Authenticate", "Basic realm=\"NicksServer\"");
        return headers;
    }

}

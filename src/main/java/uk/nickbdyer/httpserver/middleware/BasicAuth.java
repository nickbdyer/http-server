package uk.nickbdyer.httpserver.middleware;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class BasicAuth {

    private final ArrayList<String> authorisedUsers;

    public BasicAuth() {
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

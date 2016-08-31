package uk.nickbdyer.httpserver.Responses;

import uk.nickbdyer.httpserver.Method;

import java.util.List;
import java.util.stream.Collectors;

public class MethodNotAllowed implements Response {

    private final String statusLine;
    private final String responseHeader;

    public MethodNotAllowed(List<Method> allowedMethods) {
        this.statusLine = "HTTP/1.1 405 Method Not Allowed";
        this.responseHeader = createResponseHeader(allowedMethods);
    }

    @Override
    public String getResponse() {
        return getStatusLine() + getResponseHeader();
    }

    public String getStatusLine() {
        return statusLine + "\n";
    }

    private String getResponseHeader() {
        return responseHeader + "\n";
    }

    private String createResponseHeader(List<Method> allowedMethods) {
         List<String> methods = allowedMethods.stream()
            .map(Enum::toString)
            .collect(Collectors.toList());
        return "Allow: " + String.join(", ", methods) + "\n";
    }

}

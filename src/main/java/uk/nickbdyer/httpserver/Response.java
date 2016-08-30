package uk.nickbdyer.httpserver;

import java.util.List;
import java.util.stream.Collectors;

public class Response {

    private List<String> allowedMethods;
    private String statusLine;
    private String location = null;

    private Response(String statusLine) {
        this.statusLine = statusLine;
    }

    public Response(String statusLine, String location) {
        this.statusLine = statusLine;
        this.location = location;
    }

    public Response(String statusLine, List<Method> allowedMethods) {
        this.statusLine = statusLine;
        this.allowedMethods = allowedMethods.stream().map(Enum::toString).collect(Collectors.toList());
    }

    public String getStatusLine() {
        return statusLine + "\n";
    }

    public String getResponseHeader() {
        if (location != null) {
            return "Location: " + location + "\n";
        }
        if (allowedMethods != null) {
            return "Allow: " + String.join(", ", allowedMethods) + "\n";

        }
        return "";
    }

    public static Response OK() {
        return new Response("HTTP/1.1 200 OK");
    }

    public static Response NotFound() {
        return new Response("HTTP/1.1 404 Not Found");
    }

    public static Response Redirect(String location) {
        return new Response("HTTP/1.1 302 Found", location);
    }

    public static Response MethodNotAllowed(List<Method> methods) {
        return new Response("HTTP/1.1 405 Method Not Allowed", methods);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        return getStatusLine().equals(response.getStatusLine());

    }

    @Override
    public int hashCode() {
        return getStatusLine().hashCode();
    }

}

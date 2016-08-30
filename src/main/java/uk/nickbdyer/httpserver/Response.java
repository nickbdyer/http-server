package uk.nickbdyer.httpserver;

public class Response {

    private String statusLine;

    private Response(String statusLine) {
        this.statusLine = statusLine;
    }

    public String getStatusLine() {
        return statusLine;
    }

    public static Response OK() {
        return new Response("HTTP/1.1 200 OK");
    }

    public static Response NotFound() {
        return new Response("HTTP/1.1 404 Not Found");
    }

    public static Response Redirect() {
        return new Response("HTTP/1.1 302 Found");
    }
}

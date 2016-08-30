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
        return new Response("HTTP/1.1 200 OK\n");
    }

    public static Response NotFound() {
        return new Response("HTTP/1.1 404 Not Found\n");
    }

    public static Response Redirect() {
        return new Response("HTTP/1.1 302 Found\n");
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

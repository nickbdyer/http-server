package uk.nickbdyer.httpserver;

public class Response {

    private String statusLine;
    private String location = null;

    private Response(String statusLine) {
        this.statusLine = statusLine;
    }

    public Response(String statusLine, String location) {
        this.statusLine = statusLine;
        this.location = location;
    }

    public String getStatusLine() {
        return statusLine;
    }

    public String getResponseHeader() {
        if (location != null) {
            return "Location: " + location + "\n";
        }
        return "";
    }

    public static Response OK() {
        return new Response("HTTP/1.1 200 OK\n");
    }

    public static Response NotFound() {
        return new Response("HTTP/1.1 404 Not Found\n");
    }

    public static Response Redirect(String location) {
        return new Response("HTTP/1.1 302 Found", location);
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

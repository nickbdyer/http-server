package uk.nickbdyer.httpserver.Responses;

public class Redirect implements Response {

    private final String statusLine;
    private final String responseHeader;

    public Redirect(String location) {
        this.statusLine = "HTTP/1.1 302 Found";
        this.responseHeader = createResponseHeader(location);
    }

    @Override
    public String getResponse() {
        return getStatusLine() + getResponseHeader();
    }

    private String getStatusLine() {
        return statusLine + "\n";
    }

    private String getResponseHeader() {
        return responseHeader + "\n";
    }

    private String createResponseHeader(String location) {
        return "Location: " + location;
    }

}

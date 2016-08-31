package uk.nickbdyer.httpserver.Responses;

public class NotFound implements Response {

    private final String statusLine;
    private final String responseHeader;

    public NotFound() {
        this.statusLine = "HTTP/1.1 404 Not Found";
        this.responseHeader = "";
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

}

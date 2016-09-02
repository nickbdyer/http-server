package uk.nickbdyer.httpserver;

public class Response {

    private final String statusLine;
    private final String responseHeader;
    private final String responseBody;

    public Response(String statusLine, String responseHeader, String responseBody) {
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }

    public String getResponse() {
        return getStatusLine() + getResponseHeader() + getResponseBody();
    }

    public String getStatusLine() {
        return statusLine + "\n";
    }

    private String getResponseHeader() {
        return responseHeader + "\n";
    }

    public String getResponseBody() {
        return responseBody + "\n";
    }

}

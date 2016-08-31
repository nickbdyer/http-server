package uk.nickbdyer.httpserver.Responses;

public class OK implements Response {

    private final String statusLine;
    private final String responseHeader;

    public OK() {
        this.statusLine = "HTTP/1.1 200 OK";
        this.responseHeader = "";
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

}

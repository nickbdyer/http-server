package uk.nickbdyer.httpserver.responses;

public enum StatusLine {

    OK("HTTP/1.1 200 OK"),
    NOT_FOUND("HTTP/1.1 404 Not Found"),
    METHOD_NOT_ALLOWED("HTTP/1.1 405 Method Not Allowed"),
    REDIRECT("HTTP/1.1 302 Found"),
    COFFEE("HTTP/1.1 418 OK");

    private final String line;

    StatusLine(String line) {
        this.line = line;
    }

    public String getLineAsString() {
        return line;
    }
}

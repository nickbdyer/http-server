package uk.nickbdyer.httpserver;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.*;

public class Response {

    private final String statusLine;
    private String responseHeader;
    private final String responseBody;

    public Response(String statusLine, String responseHeader, String responseBody) {
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }

    public String getResponse() {
        if (responseBody != null) {
            responseHeader += "Date: " + RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT"))) + "\n";
            responseHeader += "Content-Length: " + responseBody.length() + "\n";
            responseHeader += "Content-Type: text/html; charset=utf-8";
        }
        return getStatusLine() + getResponseHeader() + getResponseBody();
    }

    public String getStatusLine() {
        return statusLine + "\n";
    }

    private String getResponseHeader() {
        return responseHeader + "\r\n\r\n";
    }

    public String getResponseBody() {
        if (responseBody != null) {
            return responseBody;
        }
        return null;
    }

}

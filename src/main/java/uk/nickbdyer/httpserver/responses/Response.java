package uk.nickbdyer.httpserver.responses;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

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
            responseHeader += "Content-Length: " + responseBody.length() + "\n";
            responseHeader += "Content-Type: text/html; charset=utf-8";
            return getStatusLine() + getResponseHeader() + getResponseBody();
        }
        return getStatusLine() + getResponseHeader();
    }

    public String getStatusLine() {
        return statusLine + "\n";
    }

    public String getResponseHeader() {
        String date = "Date: " + RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT"))) + "\n";
        return date + responseHeader + "\r\n\r\n";
    }

    public String getResponseBody() {
        return responseBody;
    }

}

package uk.nickbdyer.httpserver.responses;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

public class Response {

    private final String statusLine;
    private String header;
    private String responseBody;

    public Response(String statusLine, String header, String responseBody) {
        this.statusLine = statusLine;
        this.header = addContentTypeHeader(header);
        this.responseBody = responseBody;
    }

    public Response(String statusLine, File file) {
        this.statusLine = statusLine;
        this.header = addFileContentTypeHeader(file);
        this.responseBody = getFileBody(file);
    }


    public String getResponse() {
        if (responseBody != null) {
            header += "Content-Length: " + responseBody.length() + "\n";
            return getStatusLine() + getHeader() + getResponseBody();
        }
        return getStatusLine() + getHeader();
    }

    public String getStatusLine() {
        return statusLine + "\n";
    }

    public String getHeader() {
        String date = "Date: " + RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT"))) + "\n";
        return date + header + "\r\n\r\n";
    }

    public String getResponseBody() {
        if (responseBody != null) {
            return responseBody + "\n";
        }
        return null;
    }

    public String addFileContentTypeHeader(File file) {
        return "Content-Type: " + URLConnection.guessContentTypeFromName(file.getName()) + "\n";
    }

    public String addContentTypeHeader(String header) {
        return header + "Content-Type: text/html; charset=utf-8\n";
    }

    private String getFileBody(File file) {
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

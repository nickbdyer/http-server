package uk.nickbdyer.httpserver.responses;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;
import static uk.nickbdyer.httpserver.responses.StatusLine.*;

public class Response {

    private final String statusLine;
    private String header;
    private byte[] responseBody;

    public Response(StatusLine statusLine, String header, String responseBody) {
        this.statusLine = statusLine.getLineAsString();
        this.header = addContentTypeHeader(header);
        this.responseBody = responseBody.getBytes();
    }

    public Response(StatusLine line, File file) {
        this.statusLine = line.getLineAsString();
        this.header = addFileContentTypeHeader(file);
        this.responseBody = getFileBody(file);
    }

    public String getStatusLineAndHeader() {
        if (responseBody.length != 0) {
            header += "Content-Length: " + responseBody.length;
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

    public byte[] getResponseBody() {
        if (responseBody.length != 0) {
            return responseBody;
        }
        return null;
    }

    private String addFileContentTypeHeader(File file) {
        String type = URLConnection.guessContentTypeFromName(file.getName());
        type = (type == null ? "text/html; charset=utf-8" : type);
        return "Content-Type: " + type + "\n";
    }

    private String addContentTypeHeader(String header) {
        return header + "Content-Type: text/html; charset=utf-8\n";
    }

    private byte[] getFileBody(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            return null;
        }
    }

    public static Response NotFound() {
        return new Response(NOT_FOUND, "", "");
    }
}

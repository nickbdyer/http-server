package uk.nickbdyer.httpserver.responses;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

public class Response {

    private final int statusCode;
    private Map<String, String> header;
    private byte[] responseBody;

    public Response(int code, Map<String, String> header, String responseBody) {
        this.statusCode = code;
        this.header = addContentTypeHeader(header);
        this.responseBody = responseBody.getBytes();
    }

    public Response(int code, Map<String, String> header, File file) {
        this.statusCode = code;
        this.header = addFileContentTypeHeader(file, header);
        this.responseBody = getFileBody(file);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        header.put("Date: ", RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT"))) + "\n");
        if (responseBody.length != 0) {
            header.put("Content-Length: ", responseBody.length + "\n");
        }
        return header;
    }

    public byte[] getBody() {
        if (responseBody.length != 0) {
            return responseBody;
        }
        return null;
    }

    private Map<String, String> addFileContentTypeHeader(File file, Map<String, String> header) {
        String type = URLConnection.guessContentTypeFromName(file.getName());
        type = (type == null ? "text/html; charset=utf-8\n" : type + "\n");
        header.put("Content-Type: ", type);
        return header;
    }

    private Map<String, String> addContentTypeHeader(Map<String, String> header) {
        header.put("Content-Type: ", "text/html; charset=utf-8\n");
        return header;
    }

    private byte[] getFileBody(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            return null;
        }
    }

    public static Response NotFound() {
        return new Response(404, new HashMap<>(), "");
    }
}

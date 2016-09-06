package uk.nickbdyer.httpserver.responses;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private final int statusCode;
    private Map<String, String> headers;
    private byte[] responseBody;

    public Response(int code, Map<String, String> headers, String responseBody) {
        this.statusCode = code;
        this.headers = addContentTypeHeader(headers);
        this.responseBody = responseBody.getBytes();
    }

    public Response(int code, Map<String, String> headers, File file) {
        this.statusCode = code;
        this.headers = addFileContentTypeHeader(file, headers);
        this.responseBody = getFileBody(file);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        if (responseBody.length != 0) {
            return responseBody;
        }
        return null;
    }

    private Map<String, String> addFileContentTypeHeader(File file, Map<String, String> header) {
        String type = URLConnection.guessContentTypeFromName(file.getName());
        type = (type == null ? "text/html; charset=utf-8\n" : type);
        header.put("Content-Type", type);
        return header;
    }

    private Map<String, String> addContentTypeHeader(Map<String, String> header) {
        header.put("Content-Type", "text/html; charset=utf-8");
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

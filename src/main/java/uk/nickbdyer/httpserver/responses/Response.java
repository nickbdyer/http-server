package uk.nickbdyer.httpserver.responses;

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

    public Response(int code, Map<String, String> headers, byte[] file) {
        this.statusCode = code;
        this.headers = headers;
        this.responseBody = file;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return responseBody;
    }

    private Map<String, String> addContentTypeHeader(Map<String, String> header) {
        header.put("Content-Type", "text/html; charset=utf-8");
        return header;
    }

    public static Response NotFound() {
        return new Response(404, new HashMap<>(), "");
    }

    public static Response ServerError() {
        return new Response(500, new HashMap<>(), "");
    }
}

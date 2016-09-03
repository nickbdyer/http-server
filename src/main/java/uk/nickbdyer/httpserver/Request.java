package uk.nickbdyer.httpserver;

import java.util.Map;

public class Request {

    private Method method;
    private String path;
    private Map<String, String> headers;
    private String body;

    public Request(Method method, String path) {
        this.method = method;
        this.path = path;
    }

    public Request(RequestLine status, Map<String, String> headers) {
        this.method = status.getMethod();
        this.path = status.getPath();
        this.headers = headers;
    }

    public Request(RequestLine status, Map<String, String> headers, String body) {
        this.method = status.getMethod();
        this.path = status.getPath();
        this.headers = headers;
        this.body = body;
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}

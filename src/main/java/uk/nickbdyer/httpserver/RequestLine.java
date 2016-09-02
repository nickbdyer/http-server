package uk.nickbdyer.httpserver;

public class RequestLine {

    private final Method method;
    private final String path;

    public RequestLine(Method method, String path) {
        this.method = method;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public Method getMethod() {
        return method;
    }
}

package uk.nickbdyer.httpserver;

public class StatusLine {

    private final Method method;
    private final String path;

    public StatusLine(Method method, String path) {
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

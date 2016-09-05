package uk.nickbdyer.httpserver.requests;

public class RequestLine {

    private final Method method;
    private final String path;
    private final String parameters;

    public RequestLine(Method method, String path, String parameters) {
        this.method = method;
        this.path = path;
        this.parameters = parameters;
    }

    public String getPath() {
        return path;
    }

    public Method getMethod() {
        return method;
    }

    public String getParameters() {
        return parameters;
    }
}

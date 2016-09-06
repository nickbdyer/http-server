package uk.nickbdyer.httpserver.requests;

import java.util.Map;

public class RequestLine {

    private final Method method;
    private final String path;
    private final Map<String, String> parameters;

    public RequestLine(Method method, String path, Map<String, String> parameters) {
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

    public Map<String, String> getParameters() {
        return parameters;
    }
}

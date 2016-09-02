package uk.nickbdyer.httpserver;

public class Route {

    private final Method method;
    private final String path;
    private final Response response;

    public Route(Method method, String path) {
        this.method = method;
        this.path = path;
        this.response = new Response("HTTP/1.1 404 Not Found", "", "");
    }

    public Route(Method method, String path, Response response) {
        this.method = method;
        this.path = path;
        this.response = response;
    }

    public String getPath() {
        return path;
    }

    public Method getMethod() {
        return method;
    }

    public Response getResponse() {
        return response;
    }

    public Route thatRespondsWith(Response response) {
        return new Route(method, path, response);
    }

}

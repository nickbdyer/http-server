package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.Responses.NotFound;
import uk.nickbdyer.httpserver.Responses.Response;

public class Route {

    private final Method method;
    private final String path;
    private final Response response;

    public Route(Method method, String path) {
        this.method = method;
        this.path = path;
        this.response = new NotFound();
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

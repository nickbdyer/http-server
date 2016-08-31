package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.Responses.NotFound;
import uk.nickbdyer.httpserver.Responses.Response;

public class Request {

    private Method method;
    private String route;
    private Response response;

    public Request(Method method, String path) {
        this.method = method;
        this.route = path;
        this.response = new NotFound();
    }

    public Method getMethod() {
        return method;
    }

    public String getRoute() {
        return route;
    }

    public Request thatRespondsWith(Response response) {
        return new Request(method, route, response);
    }

    public Response getDefinedResponse() {
        return response;
    }

    private Request(Method method, String route, Response response) {
        this.method = method;
        this.route = route;
        this.response = response;
    }
}

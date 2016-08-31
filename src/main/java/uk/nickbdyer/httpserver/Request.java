package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.Responses.NotFound;
import uk.nickbdyer.httpserver.Responses.Response;

public class Request {

    private Method method;
    private String route;
    private Response response;

    public Request(Method method, String route) {
        this.method = method;
        this.route = route;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        return getMethod() == request.getMethod() && getRoute().equals(request.getRoute());
    }

    @Override
    public int hashCode() {
        int result = getMethod().hashCode();
        result = 31 * result + getRoute().hashCode();
        return result;
    }
}

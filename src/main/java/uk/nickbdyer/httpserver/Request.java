package uk.nickbdyer.httpserver;

import static uk.nickbdyer.httpserver.ConcreteResponse.*;

public class Request {

    private Method method;
    private String route;
    private ConcreteResponse response;

    public Request(Method method, String route) {
        this.method = method;
        this.route = route;
        this.response = NotFound();
    }

    private Request(Method method, String route, ConcreteResponse response) {
        this.method = method;
        this.route = route;
        this.response = response;
    }

    public Method getMethod() {
        return method;
    }

    public String getRoute() {
        return route;
    }

    public Request thatRespondsWith(ConcreteResponse response) {
        return new Request(method, route, response);
    }

    public ConcreteResponse getResponse() {
        return response;
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

package uk.nickbdyer.httpserver;

public class Request {

    private Method method;
    private String route;

    public Request(Method method, String route) {
        this.method = method;
        this.route = route;
    }

    public Method getMethod() {
        return method;
    }

    public String getRoute() {
        return route;
    }
}

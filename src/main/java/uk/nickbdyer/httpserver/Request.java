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

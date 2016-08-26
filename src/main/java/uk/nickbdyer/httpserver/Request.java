package uk.nickbdyer.httpserver;

public class Request {

    private Method method;

    public Request(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

}

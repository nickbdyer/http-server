package uk.nickbdyer.httpserver;

import java.util.function.BiFunction;

public class Route {

    private Method method;
    private String path;
    private Response response;

    private FormData body;
    private BiFunction behaviour;


    public Route(Method method, String path) {
        this.method = method;
        this.path = path;
        this.body = new FormData("");
        this.behaviour = null;
        this.response = new Response("HTTP/1.1 200 OK", "", body.getData());
    }

    public Route(Route route, BiFunction function) {
        this.method = route.getMethod();
        this.path = route.getPath();
        this.body = route.getBody();
        this.behaviour = function;
        this.response = new Response("HTTP/1.1 200 OK", "", body.getData());
    }

    public Route(Route route, FormData body) {
        this.method = route.getMethod();
        this.path = route.getPath();
        this.body = body;
        this.behaviour = route.getBehaviour();
        this.response = new Response("HTTP/1.1 200 OK", "", body.getData());
    }

    public Response getResponse(Request request) {
        if (behaviour != null) {
            return (Response) behaviour.apply(this, request);
        }
        return new Response("HTTP/1.1 200 OK", "", body.getData());
    }

    public String getPath() {
        return path;
    }

    public Method getMethod() {
        return method;
    }

    public FormData getBody() {
        return body;
    }

    private BiFunction getBehaviour() {
        return behaviour;
    }

    public Route that(BiFunction function) {
        return new Route(this, function);
    }

    public Route to(FormData data) {
        return new Route(this, data);
    }

    public Route from(FormData data) {
        return new Route(this, data);
    }

    public void setBody(String body) {
        this.body.setData(body);
    }
}

package uk.nickbdyer.httpserver.middleware;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

public abstract class Middleware {

    public Middleware next;

    public Middleware() {
        this.next = null;
    }

    public Response call(Request request) {
        return null;
    }

    public void setNext(Middleware next) {
        this.next = next;
    }

}

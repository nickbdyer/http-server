package uk.nickbdyer.httpserver.middleware;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.util.ArrayList;
import java.util.List;

public class MiddlewareStack {

    private List<Middleware> stack;

    public MiddlewareStack() {
        stack = new ArrayList<>();
    }

    public void add(Middleware middleware) {
        stack.add(middleware);
    }

    public Response call(Request request) {
        return buildStack().call(request);
    }

    private Middleware buildStack() {
        stack.add(new NotFoundMiddleware());
        for(int i = 0; i < stack.size(); i++) {
            if (i == (stack.size() - 1)) {
                break;
            }
            stack.get(i).setNext(stack.get(i + 1));
        }
        return stack.get(0);
    }
}


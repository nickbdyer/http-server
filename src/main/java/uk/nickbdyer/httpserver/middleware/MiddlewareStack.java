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
        ensureNotFoundMiddlewareIsPresent();
        return buildStack().call(request);
    }

    private Middleware buildStack() {
        for(int i = 0; i < stack.size() - 1; i++) {
            stack.get(i).setNext(stack.get(i + 1));
        }
        return stack.get(0);
    }

    private void ensureNotFoundMiddlewareIsPresent() {
        if (stack.isEmpty()) {
            stack.add(new NotFoundMiddleware());
        } else if (!(stack.get(stack.size() - 1) instanceof NotFoundMiddleware)) {
            stack.add(new NotFoundMiddleware());
        }
    }
}


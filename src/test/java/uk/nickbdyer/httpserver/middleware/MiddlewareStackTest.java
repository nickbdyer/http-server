package uk.nickbdyer.httpserver.middleware;

import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Method;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class MiddlewareStackTest {
    private MiddlewareStack stack;

    class OkMiddleware extends Middleware {
        @Override
        public Response call(Request request) {
            return new Response(200, new HashMap<>(), "");
        }
    }

    class NonRespondingMiddleware extends Middleware {
        @Override
        public Response call(Request request) {
            return next.call(request);
        }
    }

    @Before
    public void setUp() {
        stack = new MiddlewareStack();
    }
    
    @Test
    public void testReturnsResponseFromMiddleware() {
        Middleware okMiddleware = new OkMiddleware();
        stack.add(okMiddleware);

        Response response = stack.call(new Request(Method.GET, "/"));

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testReturns404AsACatchAll() {
        Middleware nonRespondingMiddleware = new NonRespondingMiddleware();
        stack.add(nonRespondingMiddleware);

        Response response = stack.call(new Request(Method.GET, "/"));

        assertEquals(404, response.getStatusCode());
    }

}
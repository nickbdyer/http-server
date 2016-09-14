package uk.nickbdyer.httpserver.middleware;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

public class NotFoundMiddleware extends Middleware {

    @Override
    public Response call(Request request) {
        return Response.NotFound();
    }

}

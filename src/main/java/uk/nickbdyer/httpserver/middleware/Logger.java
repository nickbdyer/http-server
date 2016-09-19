package uk.nickbdyer.httpserver.middleware;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Logger extends Middleware {

    private final ConcurrentLinkedQueue<String> logs;

    public Logger() {
        this.logs = new ConcurrentLinkedQueue<String>();
    }

    @Override
    public Response call(Request request) { log(request.getMethod() + " " + request.getPath() + " HTTP/1.1");
        return next.call(request);
    }

    public void log(String requestString) {
        logs.add(requestString);
    }

    public ConcurrentLinkedQueue<String> logs() {
        return logs;
    }
}

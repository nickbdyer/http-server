package uk.nickbdyer.httpserver.middleware;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Logger {

    private final ConcurrentLinkedQueue<String> logs;

    public Logger() {
        this.logs = new ConcurrentLinkedQueue<String>();
    }

    public void log(String requestString) {
        logs.add(requestString);
    }

    public ConcurrentLinkedQueue<String> logs() {
        return logs;
    }
}

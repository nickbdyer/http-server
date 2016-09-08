package uk.nickbdyer.httpserver.middleware;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private final List<String> logs;

    public Logger() {
        this.logs = new ArrayList();
    }

    public void log(String requestString) {
        logs.add(requestString);
    }

    public List<String> logs() {
        return logs;
    }
}

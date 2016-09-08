package uk.nickbdyer.httpserver.testdoubles;

import uk.nickbdyer.httpserver.middleware.Logger;

public class DummyLogger extends Logger {

    @Override
    public void log(String request) {
    }
}

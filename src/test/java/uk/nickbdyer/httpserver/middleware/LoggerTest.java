package uk.nickbdyer.httpserver.middleware;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LoggerTest {

    @Test
    public void loggerWillStoreARequestStatusLine() {
        Logger logger = new Logger();

        logger.log("GET /log HTTP/1.1");

        assertTrue(logger.logs().contains("GET /log HTTP/1.1"));
    }
}

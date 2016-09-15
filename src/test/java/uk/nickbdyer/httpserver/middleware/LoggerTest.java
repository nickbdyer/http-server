package uk.nickbdyer.httpserver.middleware;

import org.junit.Test;
import uk.nickbdyer.httpserver.requests.Request;

import static org.junit.Assert.assertTrue;
import static uk.nickbdyer.httpserver.requests.Method.GET;

public class LoggerTest {

    @Test
    public void loggerWillStoreARequestStatusLine() {
        Logger logger = new Logger();
        logger.setNext(new NotFoundMiddleware());

        logger.call(new Request(GET, "/log"));

        assertTrue(logger.logs().contains("GET /log HTTP/1.1"));
    }
}

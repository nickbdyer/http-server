import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpServerTest {

    @Test
    public void aServerHasAPort() {
        HttpServer server = new HttpServer(5000);
        assertEquals(5000, server.getPort());
    }
}

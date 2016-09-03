package uk.nickbdyer.httpserver.controllers;

import org.junit.Test;
import uk.nickbdyer.httpserver.FileController;
import uk.nickbdyer.httpserver.Request;
import uk.nickbdyer.httpserver.Response;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.Method.GET;

public class FileControllerTest {

    @Test
    public void willRespondToAGetRequest() {
        File file = new File("filetest");
        FileController controller = new FileController(file);
        Request request = new Request(GET, "/filetest");

        Response response = controller.execute(request);

        assertEquals("HTTP/1.1 200 OK\n", response.getStatusLine());
        assertEquals(null, response.getResponseBody());
    }

}

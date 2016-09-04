package uk.nickbdyer.httpserver.controllers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.requests.Method.GET;

public class FileControllerTest {

    private File file;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        file = folder.newFile("filetest");
    }

    @Test
    public void willRespondToAGetRequest() {
        FileController controller = new FileController(file);
        Request request = new Request(GET, "/filetest");

        Response response = controller.execute(request);

        assertEquals("HTTP/1.1 200 OK\n", response.getStatusLine());
    }

    @Test
    public void willReturnFileContentsInBody() throws IOException {
        Files.write(Paths.get(file.getPath()), "hello".getBytes(), StandardOpenOption.APPEND);
        FileController controller = new FileController(file);
        Request request = new Request(GET, "/filetest");

        Response response = controller.execute(request);

        assertEquals("HTTP/1.1 200 OK\n", response.getStatusLine());
        assertEquals("hello\n", response.getResponseBody());
    }

}

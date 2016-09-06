package uk.nickbdyer.httpserver.controllers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.requests.RequestLine;
import uk.nickbdyer.httpserver.responses.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;
import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.requests.Method.GET;
import static uk.nickbdyer.httpserver.requests.Method.PATCH;

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

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void willReturnFileContentsInBody() throws IOException {
        Files.write(Paths.get(file.getPath()), "hello".getBytes(), StandardOpenOption.APPEND);
        FileController controller = new FileController(file);
        Request request = new Request(GET, "/filetest");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("hello", new String(response.getBody()));
    }

    @Test
    public void willReturn204ForValidPatchEtag() throws IOException {
        Files.write(Paths.get(file.getPath()), "hello".getBytes(), StandardOpenOption.APPEND);
        FileController controller = new FileController(file);
        Map<String, String> headers = new HashMap<>();
        headers.put("If-Match", sha1Hex(Files.readAllBytes(file.toPath())));
        RequestLine statusLine = new RequestLine(PATCH, "/filetest", new HashMap<>());
        Request request = new Request(statusLine, headers, "goodbye");

        Response response = controller.execute(request);

        assertEquals(204, response.getStatusCode());
    }

    @Test
    public void fileWillBeUpdatedAfterValidPatchRequest() throws IOException {
        Files.write(Paths.get(file.getPath()), "hello".getBytes(), StandardOpenOption.APPEND);
        FileController controller = new FileController(file);
        Map<String, String> headers = new HashMap<>();
        headers.put("If-Match", sha1Hex(Files.readAllBytes(file.toPath())));
        RequestLine statusLine = new RequestLine(PATCH, "/filetest", new HashMap<>());
        Request request = new Request(statusLine, headers, "goodbye");

        controller.execute(request);

        assertEquals("goodbye", new String(Files.readAllBytes(file.toPath())));
    }

}

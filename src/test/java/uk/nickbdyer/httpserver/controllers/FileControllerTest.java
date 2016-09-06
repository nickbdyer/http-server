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
        Request request = getRequest("/filetest");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void willReturnFileContentsInBody() throws IOException {
        Files.write(Paths.get(file.getPath()), "hello".getBytes(), StandardOpenOption.APPEND);
        FileController controller = new FileController(file);
        Request request = getRequest("/filetest");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("hello", new String(response.getBody()));
    }

    @Test
    public void willRespond204ForPatchRequest() throws IOException {
        Files.write(Paths.get(file.getPath()), "hello".getBytes(), StandardOpenOption.APPEND);
        FileController controller = new FileController(file);
        Request request = patchRequest();

        Response response = controller.execute(request);

        assertEquals(204, response.getStatusCode());
    }

    @Test
    public void willRespond400ForBadPatchRequest() throws IOException {
        Files.write(Paths.get(file.getPath()), "hello".getBytes(), StandardOpenOption.APPEND);
        FileController controller = new FileController(file);
        Request request = badPatchRequest();

        Response response = controller.execute(request);

        assertEquals(400, response.getStatusCode());
    }

    //Move to file mainpulation class when extracted
    @Test
    public void fileWillBeUpdatedAfterValidPatchRequest() throws IOException {
        Files.write(Paths.get(file.getPath()), "hello".getBytes(), StandardOpenOption.APPEND);
        FileController controller = new FileController(file);
        Request request = patchRequest();

        controller.execute(request);

        assertEquals("goodbye", new String(Files.readAllBytes(file.toPath())));
    }

    private Request getRequest(String path) {
        return new Request(new RequestLine(GET, path, new HashMap<>()), new HashMap<>(), "");
    }

    private Request patchRequest() throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("If-Match", sha1Hex(Files.readAllBytes(file.toPath())));
        return new Request(new RequestLine(PATCH, "/filetest", new HashMap<>()), headers, "goodbye");
    }

    private Request badPatchRequest() throws IOException {
        return new Request(new RequestLine(PATCH, "/filetest", new HashMap<>()), new HashMap<>(), "goodbye");
    }

}

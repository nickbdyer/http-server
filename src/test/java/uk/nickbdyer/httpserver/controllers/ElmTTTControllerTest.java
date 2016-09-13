package uk.nickbdyer.httpserver.controllers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import uk.nickbdyer.httpserver.filemanager.FileFinder;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.requests.Method.GET;

public class ElmTTTControllerTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void willRespondToAGetRequest() throws IOException {
        FileFinder fileFinder = new FileFinder(temporaryFolder.newFile("index.html"));
        ElmTTTController controller = new ElmTTTController(fileFinder);
        Request request = new Request(GET, "/elmttt");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
    }

}

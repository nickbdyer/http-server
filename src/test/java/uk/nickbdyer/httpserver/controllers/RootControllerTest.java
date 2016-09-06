package uk.nickbdyer.httpserver.controllers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static uk.nickbdyer.httpserver.requests.Method.GET;
import static uk.nickbdyer.httpserver.requests.Method.HEAD;

public class RootControllerTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void willRespondToAGetRequest() {
        RootController controller = new RootController(folder.getRoot());
        Request request = new Request(GET, "/");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void willReturnThePublicDirectoryListingInTheBody() throws IOException {
        folder.newFile("fakeFile.txt");
        folder.newFile("anotherFakeFile.img");
        RootController controller = new RootController(folder.getRoot());
        Request request = new Request(GET, "/");

        Response response = controller.execute(request);

        assertThat(new String(response.getBody()), containsString("fakeFile.txt"));
        assertThat(new String(response.getBody()), containsString("anotherFakeFile.img"));
    }

    @Test
    public void willReturnThePublicDirectoryListingAsLinksInTheBody() throws IOException {
        folder.newFile("fakeFile.txt");
        folder.newFile("anotherFakeFile.img");
        RootController controller = new RootController(folder.getRoot());
        Request request = new Request(GET, "/");

        Response response = controller.execute(request);

        assertThat(new String(response.getBody()), containsString("<a href=\"/fakeFile.txt\">fakeFile.txt</a>"));
        assertThat(new String(response.getBody()), containsString("<a href=\"/anotherFakeFile.img\">anotherFakeFile.img</a>"));
    }

    @Test
    public void willRespondToAHeadRequest() {
        RootController controller = new RootController(folder.getRoot());
        Request request = new Request(HEAD, "/");

        Response response = controller.execute(request);

        assertEquals(200, response.getStatusCode());
        assertEquals(0, response.getBody().length);
    }
}

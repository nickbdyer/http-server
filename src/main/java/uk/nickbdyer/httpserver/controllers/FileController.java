package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

public class FileController extends Controller {

    private final File file;

    public FileController(File file) {
        this.file = file;
    }

    @Override
    public Response get(Request request) {
        return new Response(200, new HashMap<>(), file);
    }

    @Override
    public Response patch(Request request) {
        if (etagsMatch(request)) {
            overwriteFileWithRequestBody(request);
            return new Response(204, new HashMap<>(), "");
        }
        return new Response(400, new HashMap<>(), "");
    }

    private boolean etagIsPresent(Request request) {
        return request.getHeaders().containsKey("If-Match");
    }

    private boolean etagsMatch(Request request) {
        return etagIsPresent(request) && request.getHeaders().get("If-Match").equals(fileSha(file));
    }

    private void overwriteFileWithRequestBody(Request request) {
        try {
            FileWriter writer = new FileWriter(file, false);
            writer.write(request.getBody());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fileSha(File file) {
        try {
            return sha1Hex(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            return null;
        }

    }

}

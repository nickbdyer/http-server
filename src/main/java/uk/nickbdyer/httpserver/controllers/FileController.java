package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileController extends Controller {

    private final File file;

    public FileController(File file) {
        this.file = file;
    }

    @Override
    public Response get(Request request) {
        String body = null;
        try {
            body = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace(); }
        return new Response("HTTP/1.1 200 OK", "", body);
    }

}

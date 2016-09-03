package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.io.File;

public class FileController extends Controller {

    private final File file;

    public FileController(File file) {
        this.file = file;
    }

    @Override
    public Response get(Request request) {
        return new Response("HTTP/1.1 200 OK", "", null);
    }

}

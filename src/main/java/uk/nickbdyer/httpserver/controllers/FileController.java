package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.filemanager.FileManager;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.io.File;
import java.util.HashMap;

public class FileController extends Controller {

    private final File file;
    private final FileManager fileMan;

    public FileController(File file) {
        this.file = file;
        this.fileMan = new FileManager(file);
    }

    @Override
    public Response get(Request request) {
        if (rangeHeaderIsPresent(request)) {
            String rangeRequestHeader = request.getHeaders().get("Range");
            return new Response(206, fileMan.getFileHeaders(request.getHeaders()), fileMan.getFileContent(rangeRequestHeader));
        }
        return new Response(200, fileMan.getFileHeaders(request.getHeaders()), fileMan.getFileBytes());
    }

    @Override
    public Response patch(Request request) {
        if (fileMan.etagsMatch(request.getHeaders().getOrDefault("If-Match", ""))) {
            fileMan.overwriteFileWith(request.getBody());
            return new Response(204, new HashMap<>(), "");
        }
        return new Response(400, new HashMap<>(), "");
    }


    private boolean rangeHeaderIsPresent(Request request) {
        return request.getHeaders().containsKey("Range");
    }



}

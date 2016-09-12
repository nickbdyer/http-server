package uk.nickbdyer.httpserver.controllers;

import uk.nickbdyer.httpserver.filemanager.FileFinder;
import uk.nickbdyer.httpserver.filemanager.FileManager;
import uk.nickbdyer.httpserver.requests.Request;
import uk.nickbdyer.httpserver.responses.Response;

import java.util.HashMap;

public class ElmTTTController extends Controller {

    private final FileManager fileMan;

    public ElmTTTController(FileFinder fileFinder) {
        this.fileMan = new FileManager(fileFinder.getFile("index.html"));
    }

    @Override
    public Response get(Request request) {
        byte[] html = fileMan.getFileBytes();
        return new Response(200, new HashMap<>(), html);
    }

}

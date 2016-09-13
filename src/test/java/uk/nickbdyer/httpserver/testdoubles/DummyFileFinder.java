package uk.nickbdyer.httpserver.testdoubles;

import uk.nickbdyer.httpserver.filemanager.FileFinder;

import java.io.File;

public class DummyFileFinder extends FileFinder {

    public DummyFileFinder() {
        super(new File(""));
    }

}

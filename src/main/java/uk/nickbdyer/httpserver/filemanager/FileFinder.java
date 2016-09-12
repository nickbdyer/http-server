package uk.nickbdyer.httpserver.filemanager;

import java.io.File;
import java.util.Arrays;

public class FileFinder {
    private final File folder;

    public FileFinder(File folder) {
        this.folder = folder;
    }

    public boolean fileExists(String filename) {
        File[] files = folder.listFiles();
        return files != null && Arrays.stream(files)
                .anyMatch(file -> file.getName().equals(filename));
    }
}

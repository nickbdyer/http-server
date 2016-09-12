package uk.nickbdyer.httpserver.filemanager;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public File getFile(String filename) {
        return Arrays.stream(folder.listFiles())
                .filter(file -> Objects.equals(file.getName(), filename))
                .collect(Collectors.toList()).get(0);
    }
}

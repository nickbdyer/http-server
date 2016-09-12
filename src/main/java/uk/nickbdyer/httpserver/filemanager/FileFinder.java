package uk.nickbdyer.httpserver.filemanager;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileFinder {
    private final File folder;

    public FileFinder(File folder) {
        this.folder = folder;
    }

    public boolean fileExists(String filename) {
        try {
            if(!folder.exists()) return false;
            return Files.walk(folder.toPath())
                    .anyMatch(file -> file.getFileName().toFile().getName().equals(filename));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public File getFile(String filename) {
        try {
            return Files.walk(folder.toPath())
                    .filter(file -> Objects.equals(file.getFileName().toFile().getName(), filename))
                    .collect(Collectors.toList()).get(0).toFile();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

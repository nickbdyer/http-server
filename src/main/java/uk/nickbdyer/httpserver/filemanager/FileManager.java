package uk.nickbdyer.httpserver.filemanager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

public class FileManager {

    private final File file;

    public FileManager(File file) {
        this.file = file;
    }

    public void overwriteFileWith(String newContent) {
        try {
            FileWriter writer = new FileWriter(file, false);
            writer.write(newContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean etagsMatch(String etag) {
        return !Objects.equals("", etag) && etag.equals(fileSha(file));
    }

    private String fileSha(File file) {
        try {
            return sha1Hex(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            return null;
        }

    }

}

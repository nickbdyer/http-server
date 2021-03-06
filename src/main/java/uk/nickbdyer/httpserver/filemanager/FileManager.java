package uk.nickbdyer.httpserver.filemanager;

import uk.nickbdyer.httpserver.responses.ContentType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

public class FileManager {

    private final File file;
    private final byte[] fileBytes;

    public FileManager(File file) {
        this.file = file;
        this.fileBytes = getFileBytes();
    }

    public void overwriteFileWith(String newContent) {
        try {
            FileWriter writer = new FileWriter(file, false);
            writer.write(newContent);
            writer.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public boolean etagsMatch(String etag) {
        return !Objects.equals("", etag) && etag.equals(fileSha(file));
    }

    public Map<String, String> getFileHeaders(Map<String, String> requestHeaders) {
        Map<String, String> responseHeaders = new HashMap<>();
        if (requestHeaders.containsKey("Range")) {
            RangeFinder rangeFinder = new RangeFinder(requestHeaders.get("Range"), fileBytes.length);
            responseHeaders.put("Content-Range", "bytes " + rangeFinder.getLowerBound() + "-" + rangeFinder.getUpperBound() + "/" + fileBytes.length);
        }
        return addFileContentTypeHeader(file, responseHeaders);
    }

    public String getFileContent(String range) {
        RangeFinder rangeFinder = new RangeFinder(range, fileBytes.length);
        return new String(fileBytes).substring(rangeFinder.getLowerBound(), rangeFinder.getUpperBound() + 1);
    }

    public byte[] getFileBytes() {
        if (file == null) return new byte[]{};
        byte[] fileBytes = "".getBytes();
        try {
            fileBytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return fileBytes;
    }

    private String fileSha(File file) {
        try {
            return sha1Hex(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Map<String, String> addFileContentTypeHeader(File file, Map<String, String> header) {
        String filename = file.getName();
        String extention = filename.substring(filename.indexOf('.') + 1);
        String contentType;
        try {
            contentType = ContentType.valueOf(extention.toUpperCase()).type;
        } catch (IllegalArgumentException e) {
            contentType = "text/html";
        }
        header.put("Content-Type", contentType);
        return header;
    }

}

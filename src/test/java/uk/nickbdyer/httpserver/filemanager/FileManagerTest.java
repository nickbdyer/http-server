package uk.nickbdyer.httpserver.filemanager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;
import static org.junit.Assert.*;

public class FileManagerTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private File file;

    @Before
    public void setUp() throws IOException {
        file = folder.newFile("filetest");
    }

    @Test
    public void fileManagerCanUpdateFileContents() throws IOException {
        Files.write(Paths.get(file.getPath()), "hello".getBytes(), StandardOpenOption.APPEND);
        FileManager fileManager = new FileManager(file);

        fileManager.overwriteFileWith("Goodbye");

        assertEquals("Goodbye", new String(Files.readAllBytes(file.toPath())));
    }

    @Test
    public void fileManagerCanCheckForMatchingEtags() throws IOException {
        Files.write(Paths.get(file.getPath()), "hello".getBytes(), StandardOpenOption.APPEND);
        String fileSha = sha1Hex(Files.readAllBytes(file.toPath()));
        FileManager fileManager = new FileManager(file);

        assertTrue(fileManager.etagsMatch(fileSha));
    }

    @Test
    public void ifNoEtagIsPassedTheShaMatchWillReturnFalse() throws IOException {
        Files.write(Paths.get(file.getPath()), "hello".getBytes(), StandardOpenOption.APPEND);
        FileManager fileManager = new FileManager(file);

        assertFalse(fileManager.etagsMatch(""));
    }

    @Test
    public void fileManageWillDetectContentTypeForTextFile() throws IOException {
        File file = folder.newFile("testfile.txt");
        FileManager manager = new FileManager(file);

        Map<String, String> headers = manager.getFileHeaders(new HashMap<>());

        assertTrue(headers.containsKey("Content-Type"));
        assertEquals("text/plain", headers.get("Content-Type"));
    }

    @Test
    public void fileManagerWillDetectTheContentTypeForAnImageFile() throws IOException {
        File file = folder.newFile("testfile.png");
        FileManager manager = new FileManager(file);

        Map<String, String> headers = manager.getFileHeaders(new HashMap<>());

        assertTrue(headers.containsKey("Content-Type"));
        assertEquals("image/png", headers.get("Content-Type"));
    }

    @Test
    public void fileManagerWillDefaultContentTypeToHtmlIfNotFound() throws IOException {
        File file = folder.newFile("testfile");
        FileManager manager = new FileManager(file);

        Map<String, String> headers = manager.getFileHeaders(new HashMap<>());

        assertTrue(headers.containsKey("Content-Type"));
        assertEquals("text/html", headers.get("Content-Type"));
    }

    @Test
    public void fileManagerWillAddAppropriateRangeHeader() throws IOException {
        Files.write(Paths.get(file.getPath()), "hello".getBytes(), StandardOpenOption.APPEND);
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Range", "bytes=0-2");
        FileManager manager = new FileManager(file);

        Map<String, String> responseHeaders = manager.getFileHeaders(requestHeaders);

        assertTrue(responseHeaders.containsKey("Content-Range"));
    }

    @Test
    public void fileManagerWillReturnRangeOfFileForBody() throws IOException {
        Files.write(Paths.get(file.getPath()), "hello".getBytes(), StandardOpenOption.APPEND);
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Range", "bytes=0-2");
        FileManager manager = new FileManager(file);

        String body = manager.getFileContent("bytes=0-2");

        assertEquals("hel", body);
    }

}

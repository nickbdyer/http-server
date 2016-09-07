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

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

}

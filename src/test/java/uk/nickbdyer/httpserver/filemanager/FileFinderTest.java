package uk.nickbdyer.httpserver.filemanager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class FileFinderTest {

    private FileFinder fileFinder;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() {
        fileFinder = new FileFinder(tempFolder.getRoot());
    }

    @Test
    public void knowsIfAFileExists() throws IOException {
        tempFolder.newFile("TestFile.txt");
        assertTrue(fileFinder.fileExists("TestFile.txt"));
    }

    @Test
    public void knowsIfAFileDoesNotExist() throws IOException {
        assertFalse(fileFinder.fileExists("TestFile.txt"));
    }

    @Test
    public void canRetrieveAFile() throws IOException {
        tempFolder.newFile("TestFile.txt");

        File file = fileFinder.getFile("TestFile.txt");

        assertEquals("TestFile.txt", file.getName());
    }

    @Test
    public void canRetrieveAFileFromAFolder() throws IOException {
        tempFolder.newFolder("stylesheets");
        tempFolder.newFile("stylesheets/styles.css");

        File file = fileFinder.getFile("styles.css");

        assertEquals("styles.css", file.getName());
    }

    @Test
    public void willReturnFalseForAnUnnamedFolder() throws IOException {
        assertFalse(fileFinder.fileExists(""));
    }

}

package uk.nickbdyer.httpserver.filemanager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

//    @Test
//    public void canRetrieveAFileFromAFolder() {
//
//    }

}

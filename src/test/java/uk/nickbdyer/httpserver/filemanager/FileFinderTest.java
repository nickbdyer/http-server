package uk.nickbdyer.httpserver.filemanager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

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

//    @Test
//    public void canRetrieveAFile() {
//
//    }
//
//    @Test
//    public void canRetrieveAFileFromAFolder() {
//
//    }

}

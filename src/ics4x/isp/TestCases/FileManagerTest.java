package ics4x.isp.TestCases;
import ics4x.isp.FileManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class FileManagerTest {

    private FileManager fileManager;

    private ArrayList<File> files;
    private ArrayList<String> sendingIDs;

    private final static String desktopPath = System.getProperty("user.home") + "/Desktop/";

    public FileManagerTest(File[] files, String[] sendingIDs) {
        this.files = new ArrayList<>();
        this.sendingIDs = new ArrayList<>();
        Collections.addAll(this.files, files);
        Collections.addAll(this.sendingIDs, sendingIDs);
    }

    @Before
    public void initialize() {
        fileManager = new FileManager(files, sendingIDs);
    }

    @Parameterized.Parameters
    public static Collection input() {
        return Arrays.asList(new Object[][] {
                {new File[] {new File(desktopPath + "test sample 1.txt"),
                        new File(desktopPath + "test sample 2.txt"),
                        new File(desktopPath + "test sample 3.txt")},
                        new String[] {"John", "Peter", "Katie"}},
                {new File[] {new File(desktopPath + "test sample 1.txt"),
                        new File(desktopPath + "test sample 3.txt"),
                        new File(desktopPath + "test sample 2.txt")},
                        new String[] {"@#\"sdf", ".....", "    99v   "}},
                {new File[] {new File(desktopPath + "test sample 2.txt"),
                        new File(desktopPath + "test sample 1.txt"),
                        new File(desktopPath + "test sample 3.txt")},
                        new String[] {"Michael Schmidt", "Ernest Hemingway", " "}},
        });
    }

    @Test
    public void testAddFile() {
        File file = new File(desktopPath + "test sample 1.txt");
        files.add(file);
        fileManager.addFile(file, "Charlotte");
        assertEquals(files, fileManager.getReceivedFiles());
    }

    @Test
    public void testGetFile() {
        int index = new Random().nextInt(files.size());
        assertEquals(files.get(index), fileManager.getFile(index));
    }

    @Test
    public void testGetFileNames() {
        String[] fileNames = new String[files.size()];
        for (int i = 0; i < fileNames.length; i++) {
            fileNames[i] = files.get(i).getName();
        }
        assertArrayEquals(fileNames, fileManager.getFileNames());
    }

    @Test
    public void testGetSendingIDs() {
        ArrayList<String> actualArray = new ArrayList<>();
        Collections.addAll(actualArray, fileManager.getSendingIDs());
        assertEquals(sendingIDs, actualArray);
    }

}

package ics4x.isp.TestCases;

import ics4x.isp.Packet;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@RunWith(Parameterized.class)
public class PacketTest {

    private String sendingID;
    private String text;
    private File file;
    private ArrayList<String> array;

    private Packet packetSendingID;
    private Packet packetText;
    private Packet packetFile;
    private Packet packetArray;

    private final static String desktopPath = System.getProperty("user.home") + "/Desktop/";

    public PacketTest(String sendingID, String text, File file, String[] array) {
        this.sendingID = sendingID;
        this.text = text;
        this.file = file;
        this.array = new ArrayList<>();
        Collections.addAll(this.array, array);
    }

    @Before
    public void initialize() {
        packetSendingID = new Packet(sendingID);
        packetText = new Packet(sendingID, text);
        packetFile = new Packet(sendingID, file);
        packetArray = new Packet(sendingID, array);
    }

    @Parameterized.Parameters
    public static Collection input() {
        return Arrays.asList(new Object[][] {
                {"Peter", "I have math today.", new File(desktopPath + "test sample 1.txt"), new String[] {"John", "Ryan", "Ted"}},
                {"John", "Today is my birthday.", new File(desktopPath + "test sample 2.txt"), new String[] {"Peter", "Ryan", "Ted", "Claudia"}},
                {"Sylvia", "Apples are on sale today.", new File(desktopPath + "test sample 3.txt"), new String[] {"Ernst Ham", "Ruth Smith", "Michael Schmidt"}},
                {"1123&&id.. 3", "###5$%%6739712*&^90).", new File(desktopPath + "test sample 1.txt"), new String[] {"\"", "--__", "..sdqq_"}}
        });
    }

    @Test
    public void testGetSendingID() {
        if (!packetSendingID.getSendingID().equals(this.sendingID)) {
            fail();
        }
        if (!packetText.getSendingID().equals(this.sendingID)) {
            fail();
        }
        if (!packetFile.getSendingID().equals(this.sendingID)) {
            fail();
        }
        if (!packetArray.getSendingID().equals(this.sendingID)) {
            fail();
        }
    }

    @Test
    public void testGetTextContent() {
        assertEquals(this.text, packetText.getTextContent());
    }

    @Test
    public void testGetFile() {
        assertEquals(this.file.getPath(), packetFile.getFile().getPath());
    }

    @Test
    public void testGetContentType() {
        if (packetSendingID.getContentType() != 0) {
            fail();
        }
        if (packetText.getContentType() != 1) {
            fail();
        }
        if (packetFile.getContentType() != 2) {
            fail();
        }
        if (packetArray.getContentType() != 4) {
            fail();
        }
    }

    @Test
    public void testGetActivePeers() {
        assertEquals(this.array, packetArray.getActivePeers());
    }


}

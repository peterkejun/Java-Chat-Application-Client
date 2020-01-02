package ics4x.isp.TestCases;
import ics4x.isp.Client;
import ics4x.isp.Packet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ClientTest {

    private Client client;

    private final static String desktopPath = System.getProperty("user.home") + "/Desktop/";

    @Before
    public void setUp() {
        client = new Client("127.0.0.1");
    }

    @Test
    public void testProcessPacket() {
        Packet packet = new Packet("me", new File(desktopPath + "test sample 1.txt"));
        int numFiles = client.getFileManager().getFileNames().length;
        client.processPacket(packet);
        assertEquals(numFiles + 1, client.getFileManager().getFileNames().length);
    }

}

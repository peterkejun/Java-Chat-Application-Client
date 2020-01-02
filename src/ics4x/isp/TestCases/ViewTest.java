package ics4x.isp.TestCases;

import ics4x.isp.Client;
import ics4x.isp.View.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ViewTest {

    private Client Client;


    @Before
    public void setUp() throws Exception {
        Client = new Client("127.0.0.1");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void ChatWindow() {
        ClientFrame Chat = new ClientFrame(Client);
        Assert.assertNotNull(Chat.getChatWindow());
    }

    @Test
    public void FileWindow() {
        ClientFrame Window = new ClientFrame(Client);
        Assert.assertNotNull(Window.getFileWindow());
    }

    @Test
    public void PeerStatusWindow() {
        ClientFrame Status = new ClientFrame(Client);
        Assert.assertNotNull(Status.getPeerStatusWindow());
    }

}

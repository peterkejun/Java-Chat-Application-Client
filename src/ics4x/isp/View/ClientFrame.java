/*
Summative: Culminating Project 2019
Author: Peter Ke, Ernst Mach
Date of completion: May 26
Description: This class is the main chat frame with a chat window, a peer status window and a file window. The controller
            uses this class to access different UI components. This frame is only displayed after user has entered
            his unique username.
 */

package ics4x.isp.View;

import ics4x.isp.Client;

import javax.swing.*;
import java.awt.*;

public class ClientFrame extends JFrame {

    private Client controller;          //reference to controller

    private ChatWindow chatWindow;      //an instance of chat window

    private PeerStatusWindow peerStatusWindow;      //an instance of peer status window

    private FileWindow fileWindow;      //an instance of file window

    public ClientFrame(Client controller) {
        this.controller = controller;

        //window is closed on clicking the close button
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //set layout manager to Border layout
        this.setLayout(new BorderLayout());

        //create chat window and put it in the middle
        chatWindow = new ChatWindow(controller);
        chatWindow.setVisible(true);
        this.add(chatWindow, BorderLayout.CENTER);

        //create peer status window and put it on the left side
        peerStatusWindow = new PeerStatusWindow(controller);
        peerStatusWindow.setVisible(true);
        this.add(peerStatusWindow, BorderLayout.WEST);

        //create file window and put it on the right side
        fileWindow = new FileWindow(controller);
        fileWindow.setVisible(true);
        this.add(fileWindow, BorderLayout.EAST);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(false);
        this.setResizable(false);
    }

    //returns the chat window
    public ChatWindow getChatWindow() {
        return chatWindow;
    }

    //returns the peer status window
    public PeerStatusWindow getPeerStatusWindow() {
        return peerStatusWindow;
    }

    //returns the file window
    public FileWindow getFileWindow() {
        return fileWindow;
    }
}

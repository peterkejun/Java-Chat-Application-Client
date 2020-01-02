/*
Summative: Culminating Project 2019
Author: Peter Ke, Ernst Mach
Date of completion: June 3
Description: This class is the controller of the program and the entrance point. First, it initializes View and responds
            to user's instructions such as sending a text, sending a file and opening a file. Second, it's responsible
            for socket connection with the server. It waits and reads Packets from server and processes the Packets by
            their content type. For example, it adds a new message to View when a text message is received, it saves a
            new file in FileManager, and it updates the peer status window table when a new array of active peers is
            received. It also sends Packets to server.
 */

package ics4x.isp;

import ics4x.isp.View.ClientFrame;
import ics4x.isp.View.LoginWindow;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        // write your code here
        new Client("127.0.0.1");
    }

    private String clientID;        //the unique ID of this client

    private String serverIP;        //IP of the server
    private Socket socket;          //socket to read and write to server
    private final int port = 6789;  //port of the connection

    private ObjectInputStream inputStream;      //stream to read from server
    private ObjectOutputStream outputStream;    //stream to write to server

    private ClientFrame clientFrame;    //View of chat
    private LoginWindow loginWindow;    //View of login

    private FileManager fileManager;    //file manager to store received files

    public Client(String serverIP) {
        this.serverIP = serverIP;
        fileManager = new FileManager();
        clientFrame = new ClientFrame(this);
        clientFrame.setVisible(false);
        loginWindow = new LoginWindow(this);
        loginWindow.setVisible(true);
        //start connection to server
        startRunning();
    }

    //thread-safe method to write a text packet to server
    public synchronized void requestSendMessage(String message) {
        try {
            //create packet with text
            Packet packet = new Packet(clientID, message);
            //write packet to output stream to server
            outputStream.writeObject(packet);
            //add text to dialog text area
            clientFrame.getChatWindow().addToChatArea("You", message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //thread-safe method to write a file packet to server
    public synchronized void requestSendFile(File file) {
        try {
            //create packet with file
            Packet packet = new Packet(clientID, file);
            //write packet to output stream to server
            outputStream.writeObject(packet);
            //add notification to dialog text area
            clientFrame.getChatWindow().addToChatArea("You sent a file: \"" + file.getName() + "\"");
        } catch (IOException exc) {

        }
    }

    //thread-safe method to open a file using OS default program and save the file to user's desktop
    public synchronized void requestOpenFile(int fileIndex) {
        //get file of given index
        File file = fileManager.getFile(fileIndex);
        try {
            //save file to desktop and open with OS default program
            Desktop.getDesktop().open(file);
        } catch (IOException exc) {
            System.out.println("failed to open " + file.getPath());
        }
    }

    //shows View for chat, hides View for login, and sends client ID to server
    public void usernameInputFinished(String username) {
        clientID = username;
        loginWindow.setVisible(false);
        clientFrame.getChatWindow().setClientUsername(username);
        clientFrame.setTitle(username);
        clientFrame.setVisible(true);
        sendClientID();
    }

    //starts connection to server and keeps reading from server
    private void startRunning() {
        System.out.print("running");
        clientFrame.getChatWindow().setStatus("Attempting Connection ...");
        //try socket connection
        try {
            socket = new Socket(serverIP, port);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Server Might Be Down!","Warning",JOptionPane.WARNING_MESSAGE);
        }
        clientFrame.getChatWindow().setStatus("Connected to: " + socket.getInetAddress().getHostName());

        //initialize input and output streams with socket's input and output streams
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        while (true) {
            try {
                //wait and read packet from server
                Packet packet = (Packet) inputStream.readObject();
                //process the packet by its content type
                processPacket(packet);
            } catch (ClassNotFoundException | IOException exc) {
                //exc.printStackTrace();
            }
        }
    }

    //processes packet from server by its content type
    public void processPacket(Packet packet) {
        //return if packet has no sending ID
        if (packet.getSendingID() == null || packet.getSendingID().isEmpty())
            return;

        switch (packet.getContentType()) {
            //if text, display in dialog text area
            case 1:
                if (packet.getTextContent().isEmpty())
                    return;
                clientFrame.getChatWindow().addToChatArea(packet.getSendingID(), packet.getTextContent());
                //if file, save in file manager and display in file window
            case 2:
                if (packet.getFile().getPath().equals(""))
                    return;
                System.out.println("received file from " + packet.getSendingID() + ": " + packet.getFile().getName());
                clientFrame.getChatWindow().addToChatArea(packet.getSendingID() + " sent you a file.");
                fileManager.addFile(packet.getFile(), packet.getSendingID());
                clientFrame.getFileWindow().updateTable(fileManager.getFileNames(), fileManager.getSendingIDs());
                //if array of active peers, update peer status window
            case 4:
                clientFrame.getPeerStatusWindow().updateTable(packet.getActivePeers());
        }
    }

    //sends client ID to the server for registration
    private void sendClientID() {
        try {
            System.out.println("id: " + clientID);
            //create packet with client ID
            Packet packet = new Packet(clientID);
            //write packet to output stream to server
            outputStream.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //returns the file manager
    public FileManager getFileManager() {
        return fileManager;
    }

}


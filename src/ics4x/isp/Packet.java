/*
Summative: Culminating Project 2019
Author: Peter Ke, Ernst Mach
Date of completion: June 2
Description: A packet can store four types of data, a String sending ID, a String text content, a File object and an
            Arraylist of String. Each instance of packet specifies which data it is carrying over the network. (See
            Content Type below) The four types of data correspond to four functionality of the Server-Client
            interaction, including registering in server with a unique client ID, sending a text message, sending a
            file, sending an array of active peers. The packet object implements Serializable to allow Java to convert
            this object into byte stream before sending it through sockets.
 */

package ics4x.isp;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Packet implements Serializable {

    private String sendingID;                                       //ID of the user who sends this packet
    private String textContent = "";                                //text content of this packet
    private File file = new File("");                       //file content of this packet
    private ArrayList<String> activePeers = new ArrayList<>();          //array of active peers of this packet

    private int contentType = 1;                                //which type of content does this packet hold
    /*
    Content Type:
    0 : id
    1 : text (default)
    2 : file
    4 : active peers
     */

    //constructor for packet that holds just the sending ID
    public Packet(String sendingID) {
        this.sendingID = sendingID;
        this.contentType = 0;
    }

    //constructor for packet that holds text content
    public Packet(String sendingID, String textContent) {
        this.sendingID = sendingID;
        this.textContent = textContent;
        this.contentType = 1;
    }

    //constructor for packet that holds file content
    public Packet(String sendingID, File file) {
        this.sendingID = sendingID;
        this.file = file;
        this.contentType = 2;
    }

    //constructor for packet that holds an array of active peers
    public Packet(String sendingID, ArrayList<String> activePeers) {
        this.sendingID = sendingID;
        this.activePeers.addAll(activePeers);
        this.contentType = 4;
    }

    //returns the sending ID
    public String getSendingID() {
        return sendingID;
    }

    //returns the text content
    public String getTextContent() {
        return textContent;
    }

    //returns the file
    public File getFile() {
        return file;
    }

    //returns the content type
    public int getContentType() {
        return contentType;
    }

    //returns the array of active peers
    public ArrayList<String> getActivePeers() {
        return activePeers;
    }

}
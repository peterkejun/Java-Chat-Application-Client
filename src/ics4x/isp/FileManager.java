/*
Summative: Culminating Project 2019
Author: Peter Ke, Ernst Mach
Date of completion: June 2
Description: This class is a storage of all received files. The controller (Client class) adds a file to FileManager each
            time a new file is received, and the controller can retrieve the file with an index. The main purpose of
            FileManager is to populate the file table.
 */

package ics4x.isp;

import java.io.File;
import java.util.ArrayList;

public class FileManager {

    private ArrayList<File> receivedFiles;      //array to store received files
    private ArrayList<String> sendingIDs;       //array to store corresponding sending IDs

    public FileManager() {
        //initialize both empty arrays
        receivedFiles = new ArrayList<>();
        sendingIDs = new ArrayList<>();
    }

    //for testing
    public FileManager(ArrayList<File> receivedFiles, ArrayList<String> sendingIDs) {
        this.receivedFiles = new ArrayList<>();
        this.sendingIDs = new ArrayList<>();
        this.receivedFiles.addAll(receivedFiles);
        this.sendingIDs.addAll(sendingIDs);
    }

    //adds a file and its corresponding sending ID to the arrays
    public void addFile(File file, String sendingID) {
        receivedFiles.add(file);
        sendingIDs.add(sendingID);
    }

    //returns the file at the given index
    public File getFile(int index) {
        return receivedFiles.get(index);
    }

    //returns all files' names
    public String[] getFileNames() {
        String[] fileNames = new String[receivedFiles.size()];
        for (int i = 0; i < receivedFiles.size(); i++) {
            fileNames[i] = receivedFiles.get(i).getName();
        }
        return fileNames;
    }

    //returns all sending IDs
    public String[] getSendingIDs() {
        String[] IDs = new String[sendingIDs.size()];
        for (int i = 0; i < sendingIDs.size(); i++) {
            IDs[i] = sendingIDs.get(i);
        }
        return IDs;
    }

    //for testing
    public ArrayList<File> getReceivedFiles() {
        return receivedFiles;
    }

}

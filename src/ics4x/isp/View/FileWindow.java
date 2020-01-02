/*
Summative: Culminating Project 2019
Author: Peter Ke, Ernst Mach
Date of completion: May 31
Description: This view class is a JPanel which displays all files received using a JTable. The controller updates the
            table with a new array of file names and corresponding sending IDs. When user clicks on a file in the table,
            this class calls the controller's method to open selected file using OS default program.
 */

package ics4x.isp.View;

import ics4x.isp.Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FileWindow extends JPanel {

    private Client controller;      //reference to controller

    private JTable fileTable;       //table to display received files
    private JLabel titleLabel;      //table to display "Files Received"

    public FileWindow(Client controller) {
        this.controller = controller;
        initComponents();               //initialize UI components
        setVisible(false);
    }

    private void initComponents() {
        //disable flow layout on panel
        this.setLayout(null);

        //"Files Received" label
        titleLabel = new JLabel("Files Received");
        titleLabel.setForeground(Color.black);
        titleLabel.setBounds(10, 10, 200, 30);
        this.add(titleLabel);

        //table is uneditable by the user
        fileTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        fileTable.setPreferredScrollableViewportSize(new Dimension(200, 300));
        fileTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(fileTable);        //scroll pane to enable scrolling on table
        scrollPane.setBounds(10, 50, 250, 350);
        this.add(scrollPane);
    }

    //a new preferred size for this panel
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 425);
    }

    //updates files table with an array of file names and an array of corresponding sending IDs
    public void updateTable(String[] fileNames, String[] sendingIDs) {
        //create a new table model with dynamic rows and 2 columns
        DefaultTableModel dtm = new DefaultTableModel(0, 2);
        //add each file name and its corresponding sending ID to a row
        for (int i = 0; i < fileNames.length; i++) {
            dtm.addRow(new Object[] {fileNames[i], sendingIDs[i]});
        }
        //set the table model on the table
        fileTable.setModel(dtm);
        //listens to a click event on any row of the table
        fileTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //get the selected row
                int row = fileTable.getSelectedRow();
                //if no row is selected, return
                if (row == -1)
                    return;
                //call thread-safe method on controller to open the selected file
                controller.requestOpenFile(row);
                //clear selection to listen to next click event
                fileTable.clearSelection();
            }
        });
    }

}

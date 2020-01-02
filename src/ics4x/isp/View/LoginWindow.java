/*
Summative: Culminating Project 2019
Author: Peter Ke, Ernst Mach
Date of completion: May 25, 2019
Description: This view class displays a login window where the user has to type in his unique username to start
            chatting. This frame is displayed before the chat frame. It uses a JTextField to read user's input from
            keyboard and sends the username to controller, which is later sent to server for registration.
 */

package ics4x.isp.View;

import ics4x.isp.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {

    private Client controller;          //reference to controller

    private JTextField inputTextField;      //text field to read user's client ID

    public LoginWindow(Client controller) {
        this.controller = controller;
        this.setVisible(false);
        this.setTitle("Log In");
        this.setLayout(new FlowLayout());

        initComponents();

        this.setSize(400, 100);
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        //label that shows "Log In"
        JLabel loginLabel = new JLabel("Log In");
        this.add(loginLabel);

        //label that shows "Username: "
        JLabel usernameLabel = new JLabel("Username: ");
        this.add(usernameLabel);
        inputTextField = new JTextField();
        inputTextField.setPreferredSize(new Dimension(100, 30));
        inputTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //call method on controller to close log in window and show chat window
                controller.usernameInputFinished(inputTextField.getText());
            }
        });
        this.add(inputTextField);
    }


}

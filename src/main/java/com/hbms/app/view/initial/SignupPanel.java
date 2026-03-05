package com.hbms.app.view.initial;

import com.hbms.app.controller.UserController;
import com.hbms.app.view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class SignupPanel extends JPanel {
    private JLabel lblMessage;
    private final UserController userController;

    public SignupPanel(MainFrame frame, UserController userController){
        this.userController = userController;
        setLayout(new GridLayout(5,2,10,10));

        JTextField tfFirstName=new JTextField();
        JTextField tfLastName=new JTextField();
        JTextField tfEmail=new JTextField();
        JPasswordField pfPassword=new JPasswordField();

        lblMessage=new JLabel("");
        lblMessage.setForeground(Color.RED);

        JButton btnSignup=new JButton("Sign up");
        JButton btnBack=new JButton("Back");

        add(new JLabel("First name"));
        add(tfFirstName);
        add(new JLabel("Last name"));
        add(tfLastName);
        add(new JLabel("Email"));
        add(tfEmail);
        add(new JLabel("Password"));
        add(pfPassword);

        add(lblMessage);
        add(new JLabel(""));

        add(btnSignup);
        add(btnBack);

        btnBack.addActionListener(e -> frame.showScreen("START"));

        btnSignup.addActionListener(e -> {
            String firstName=tfFirstName.getText().trim();
            String lastName=tfLastName.getText().trim();
            String email=tfEmail.getText().trim();
            String password=new String(pfPassword.getPassword()).trim();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
                lblMessage.setText("Error! Empty fields.");
                return;
            }

            if (!email.contains("@")){
                lblMessage.setText("Error! Invalid Email");
                return;
            }

            if (password.length()<8){
                lblMessage.setText("Error! Password must be of 8 characters.");
                return;
            }

            boolean result =  userController.signup(firstName, lastName, email, password);

            if (result){
                lblMessage.setForeground(Color.GREEN);
                lblMessage.setText("Signup successful.");
            }
            else {
                lblMessage.setForeground(Color.RED);
                lblMessage.setText("Signup failed.");
            }
        });
    }
}

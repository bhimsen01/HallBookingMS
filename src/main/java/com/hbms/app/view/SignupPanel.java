package com.hbms.app.view;

import com.hbms.app.controller.UserController;

import javax.swing.*;
import java.awt.*;

public class SignupPanel extends JPanel {
    private JLabel lblError;
    private final UserController userController;

    public SignupPanel(MainFrame frame, UserController userController){
        this.userController = userController;
        setLayout(new GridLayout(5,2,10,10));

        JTextField tfFirstName=new JTextField();
        JTextField tfLastName=new JTextField();
        JTextField tfEmail=new JTextField();
        JPasswordField pfPassword=new JPasswordField();

        lblError=new JLabel("");
        lblError.setForeground(Color.RED);

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

        add(lblError);
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
                lblError.setText("Error! Empty fields.");
                return;
            }

            if (!email.contains("@")){
                lblError.setText("Error! Invalid Email");
                return;
            }

            if (password.length()<8){
                lblError.setText("Error! Password must be of 8 characters.");
                return;
            }

            String result =  userController.signup(firstName, lastName, email, password);

            if (result.equals("Signup successful.")){
                lblError.setForeground(Color.GREEN);
            }
            else {
                lblError.setForeground(Color.RED);
            }

            lblError.setText(result);
        });
    }
}

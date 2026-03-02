package com.hbms.app.view;

import com.hbms.app.auth.AuthUser;
import com.hbms.app.controller.UserController;
import com.hbms.app.model.User;
import com.hbms.app.session.Session;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JLabel lblError;
    private final UserController userController;

    public LoginPanel(MainFrame frame, UserController userController){
        this.userController=userController;
        setLayout(new GridLayout(4,2,10,10));

        JTextField tfEmail=new JTextField();
        JPasswordField pfPassword=new JPasswordField();

        lblError=new JLabel("");
        lblError.setForeground(Color.RED);

        JButton btnLogin=new JButton("Login");
        JButton btnBack=new JButton("Back");

        add(new JLabel("Email"));
        add(tfEmail);
        add(new JLabel("Password"));
        add(pfPassword);

        add(lblError);
        add(new JLabel(""));

        add(btnLogin);
        add(btnBack);

        btnBack.addActionListener(e-> frame.showScreen("START"));

        btnLogin.addActionListener(e-> {
            String email=tfEmail.getText().trim();
            String password=new String(pfPassword.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()){
                lblError.setText("Error! Empty fields.");
                return;
            }

            if (!email.contains("@")){
                lblError.setText("Error! Invalid Email");
                return;
            }

            System.out.println("Login clicked: "+email);

            try{
                AuthUser authUser = userController.login(email, password);
                lblError.setForeground(Color.GREEN);
                lblError.setText("Login successful.");

                if (authUser!=null){
                    Session.login(authUser);
                    frame.showScreen("HOME");
                }
            } catch (IllegalArgumentException exception){
                lblError.setForeground(Color.RED);
                lblError.setText("Incorrect email or password.");
            }
        });
    }
}

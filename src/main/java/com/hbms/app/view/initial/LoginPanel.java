package com.hbms.app.view.initial;

import com.hbms.app.auth.AuthUser;
import com.hbms.app.controller.UserController;
import com.hbms.app.session.Session;
import com.hbms.app.view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JLabel lblMessage;
    private final UserController userController;

    public LoginPanel(MainFrame frame, UserController userController){
        this.userController=userController;
        setLayout(new GridLayout(4,2,10,10));

        JTextField tfEmail=new JTextField();
        JPasswordField pfPassword=new JPasswordField();

        lblMessage=new JLabel("");
        lblMessage.setForeground(Color.RED);

        JButton btnLogin=new JButton("Login");
        JButton btnBack=new JButton("Back");

        add(new JLabel("Email"));
        add(tfEmail);
        add(new JLabel("Password"));
        add(pfPassword);

        add(lblMessage);
        add(new JLabel(""));

        add(btnLogin);
        add(btnBack);

        btnBack.addActionListener(e-> frame.showScreen("START"));

        btnLogin.addActionListener(e-> {
            String email=tfEmail.getText().trim();
            String password=new String(pfPassword.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()){
                lblMessage.setText("Error! Empty fields.");
                return;
            }

            if (!email.contains("@")){
                lblMessage.setText("Error! Invalid Email");
                return;
            }

            System.out.println("Login clicked: "+email);

            try{
                AuthUser authUser = userController.login(email, password);
                lblMessage.setForeground(Color.GREEN);
                lblMessage.setText("Login successful.");
                tfEmail.setText("");
                pfPassword.setText("");

                if (authUser!=null){
                    Session.login(authUser);
                    frame.showDashboard();
                }
            } catch (IllegalArgumentException exception){
                lblMessage.setForeground(Color.RED);
                lblMessage.setText("Incorrect email or password.");
            }
        });
    }
}

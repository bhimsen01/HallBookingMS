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

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        // Title
        JLabel lblTitle = new JLabel("Log in with your account");
        lblTitle.setFont(new Font("Inter", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        add(lblTitle, gbc);

        JTextField tfEmail=new JTextField();
        JPasswordField pfPassword=new JPasswordField();

        tfEmail.setPreferredSize(new Dimension(260, 32));
        pfPassword.setPreferredSize(new Dimension(260, 32));

        // Email
        gbc.gridy = 5;
        add(new JLabel("Email"), gbc);

        gbc.gridy = 6;
        add(tfEmail, gbc);

        // Password
        gbc.gridy = 7;
        add(new JLabel("Password"), gbc);

        gbc.gridy = 8;
        add(pfPassword, gbc);

        // Message
        lblMessage = new JLabel("");
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 9;
        add(lblMessage, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton btnLogin=new JButton("Log in");
        JButton btnBack=new JButton("Cancel");

        btnLogin.setPreferredSize(new Dimension(120, 40));
        btnBack.setPreferredSize(new Dimension(120, 40));

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnBack);

        gbc.gridy = 10;
        gbc.insets = new Insets(20, 0, 0, 0);
        add(buttonPanel, gbc);

        btnBack.addActionListener(e-> frame.showScreen("START"));

        btnLogin.addActionListener(e-> {
            String email=tfEmail.getText().trim();
            String password=new String(pfPassword.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()){
                lblMessage.setForeground(Color.RED);
                lblMessage.setText("Empty fields.");
                return;
            }

            if (!email.contains("@")){
                lblMessage.setForeground(Color.RED);
                lblMessage.setText("Invalid email.");
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

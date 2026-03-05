package com.hbms.app.view.initial;

import com.hbms.app.view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    public StartPanel(MainFrame frame){
        setLayout(new BorderLayout());

        JLabel title=new JLabel("Hall Symphony System");
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel buttonPanel=new JPanel();
        JButton btnLogin=new JButton("Log in");
        JButton btnSignup=new JButton("Sign up");

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnSignup);

        add(title, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> frame.showScreen("LOGIN"));
        btnSignup.addActionListener(e -> frame.showScreen("SIGNUP"));
    }
}

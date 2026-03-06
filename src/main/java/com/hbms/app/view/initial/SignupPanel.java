package com.hbms.app.view.initial;

import com.hbms.app.controller.UserController;
import com.hbms.app.view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class SignupPanel extends JPanel {

    private JLabel lblMessage;
    private final UserController userController;

    public SignupPanel(MainFrame frame, UserController userController) {
        this.userController = userController;

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        // Title
        JLabel lblTitle = new JLabel("Create Your Account");
        Font currentFont = lblTitle.getFont();
        Font newFont = currentFont.deriveFont(28f);
        lblTitle.setFont(newFont);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        add(lblTitle, gbc);

        JTextField tfFirstName = new JTextField();
        JTextField tfLastName = new JTextField();
        JTextField tfEmail = new JTextField();
        JPasswordField pfPassword = new JPasswordField();

        tfFirstName.setPreferredSize(new Dimension(260, 32));
        tfLastName.setPreferredSize(new Dimension(260, 32));
        tfEmail.setPreferredSize(new Dimension(260, 32));
        pfPassword.setPreferredSize(new Dimension(260, 32));

        // First name
        gbc.gridy = 1;
        add(new JLabel("First name"), gbc);

        gbc.gridy = 2;
        add(tfFirstName, gbc);

        // Last name
        gbc.gridy = 3;
        add(new JLabel("Last name"), gbc);

        gbc.gridy = 4;
        add(tfLastName, gbc);

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

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        JButton btnSignup = new JButton("Sign up");
        JButton btnBack = new JButton("Cancel");

        btnSignup.setPreferredSize(new Dimension(120, 40));
        btnSignup.setBackground(new Color(0, 145, 255));
        btnBack.setPreferredSize(new Dimension(120, 40));

        buttonPanel.add(btnSignup);
        buttonPanel.add(btnBack);

        gbc.gridy = 10;
        gbc.insets = new Insets(20, 0, 0, 0);
        add(buttonPanel, gbc);

        // Actions
        btnBack.addActionListener(e -> frame.showScreen("START"));

        btnSignup.addActionListener(e -> {

            String firstName = tfFirstName.getText().trim();
            String lastName = tfLastName.getText().trim();
            String email = tfEmail.getText().trim();
            String password = new String(pfPassword.getPassword()).trim();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                lblMessage.setForeground(new Color(255, 66,69));
                lblMessage.setText("Empty fields.");
                return;
            }

            if (!email.contains("@")) {
                lblMessage.setForeground(new Color(255, 66,69));
                lblMessage.setText("Invalid Email");
                return;
            }

            if (password.length() < 8) {
                lblMessage.setForeground(new Color(255, 66,69));
                lblMessage.setText("Password must be at least 8 characters.");
                return;
            }

            boolean result = userController.signup(firstName, lastName, email, password);

            if (result) {
                lblMessage.setForeground(new Color(48, 209,88));
                lblMessage.setText("Signup successful.");
            } else {
                lblMessage.setForeground(new Color(255, 66,69));
                lblMessage.setText("Signup failed.");
            }
        });
    }
}
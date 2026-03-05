package com.hbms.app.view.profile;

import com.hbms.app.auth.AuthUser;
import com.hbms.app.session.Session;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProfilePanel extends JPanel {
    private com.hbms.app.controller.UserController userController;
    private JLabel lblFirstName;
    private JLabel lblLastName;
    private JLabel lblEmail;
    private JLabel lblRole;

    public ProfilePanel(com.hbms.app.controller.UserController userController) {
        this.userController = userController;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        AuthUser currentUser = Session.getCurrentUser();

        // Main card - Display info as text
        JPanel card = new JPanel(new GridLayout(5, 2, 10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        lblFirstName = new JLabel(currentUser.getFirstName());
        lblLastName = new JLabel(currentUser.getLastName());
        lblEmail = new JLabel(currentUser.getEmail());
        lblRole = new JLabel(currentUser.getRole().toString());

        card.add(new JLabel("First Name:"));
        card.add(lblFirstName);
        card.add(new JLabel("Last Name:"));
        card.add(lblLastName);
        card.add(new JLabel("Email:"));
        card.add(lblEmail);
        card.add(new JLabel("Role:"));
        card.add(lblRole);
        card.add(new JLabel(""));

        JButton btnUpdate = new JButton("Update Profile");
        btnUpdate.addActionListener(e -> openUpdateDialog(currentUser));

        card.add(btnUpdate);

        add(card, BorderLayout.NORTH);
    }

    private void openUpdateDialog(AuthUser currentUser) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Update Profile", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(SwingUtilities.getWindowAncestor(this));
        dialog.setLayout(new GridLayout(4, 2, 10, 10));

        JTextField tfFirstName = new JTextField(currentUser.getFirstName());
        JTextField tfLastName = new JTextField(currentUser.getLastName());
        JTextField tfEmail = new JTextField(currentUser.getEmail());
        JTextField tfRole = new JTextField(currentUser.getRole().toString());
        tfRole.setEditable(false);

        dialog.add(new JLabel("First Name:"));
        dialog.add(tfFirstName);
        dialog.add(new JLabel("Last Name:"));
        dialog.add(tfLastName);
        dialog.add(new JLabel("Email:"));
        dialog.add(tfEmail);
        dialog.add(new JLabel("Role:"));
        dialog.add(tfRole);

        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        dialog.add(btnPanel);

        btnCancel.addActionListener(e -> dialog.dispose());

        btnSave.addActionListener(e -> {
            String firstName = tfFirstName.getText().trim();
            String lastName = tfLastName.getText().trim();
            String email = tfEmail.getText().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields.");
                return;
            }

            com.hbms.app.model.User updatedUser = new com.hbms.app.model.User(
                    currentUser.getUserId(),
                    firstName,
                    lastName,
                    email,
                    null,
                    null,
                    0,
                    null
            );

            userController.updateUserDetails(currentUser.getUserId(), updatedUser);
            JOptionPane.showMessageDialog(dialog, "Profile updated successfully.");

            // Update labels
            lblFirstName.setText(firstName);
            lblLastName.setText(lastName);
            lblEmail.setText(email);

            dialog.dispose();
        });

        dialog.setVisible(true);
    }
}

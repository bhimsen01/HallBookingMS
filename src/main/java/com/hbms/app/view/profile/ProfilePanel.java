package com.hbms.app.view.profile;

import com.hbms.app.auth.AuthUser;
import com.hbms.app.session.Session;
import com.hbms.app.view.MainFrame;

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

        AuthUser currentUser = Session.getCurrentUser();

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Main card - Display info as text
        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(50,50,100));
                g2.fillRoundRect(0,0,getWidth(),getHeight(),20,20);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        card.setLayout(new GridLayout(4, 2, 10, 10));
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

        Font labelFont = new Font("Inter", Font.PLAIN, 14);

        lblFirstName = new JLabel(currentUser.getFirstName());
        lblLastName = new JLabel(currentUser.getLastName());
        lblEmail = new JLabel(currentUser.getEmail());
        lblRole = new JLabel(currentUser.getRole().toString());

        JLabel[] labels = {new JLabel("First Name:"), lblFirstName, new JLabel("Last Name:"), lblLastName, new JLabel("Email:"), lblEmail, new JLabel("Role:"), lblRole};

        for (JLabel lbl : labels) {
            lbl.setForeground(Color.WHITE);
            lbl.setFont(labelFont);
        }

        card.add(new JLabel("First Name:"));
        card.add(lblFirstName);
        card.add(new JLabel("Last Name:"));
        card.add(lblLastName);
        card.add(new JLabel("Email:"));
        card.add(lblEmail);
        card.add(new JLabel("Role:"));
        card.add(lblRole);

        container.add(card);
        container.add(Box.createVerticalStrut(15));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        JButton btnUpdate = new JButton("Update Profile");
        JButton btnLogout = new JButton("Logout");

        btnUpdate.setPreferredSize(new Dimension(120, 40));
        btnLogout.setPreferredSize(new Dimension(120, 40));

        btnUpdate.addActionListener(e -> openUpdateDialog(currentUser));
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION){
                Session.logout();
                if (SwingUtilities.getWindowAncestor(this) instanceof MainFrame) {
                    ((MainFrame) SwingUtilities.getWindowAncestor(this)).showScreen("START");
                }
            }
        });

        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnLogout);

        container.add(buttonPanel);

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
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

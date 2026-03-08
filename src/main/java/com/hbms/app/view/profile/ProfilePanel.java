package com.hbms.app.view.profile;

import com.hbms.app.auth.AuthUser;
import com.hbms.app.controller.UserController;
import com.hbms.app.dao.UserDAO;
import com.hbms.app.model.User;
import com.hbms.app.session.Session;
import com.hbms.app.view.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProfilePanel extends JPanel {
    private UserController userController;
    private UserDAO userDAO;
    private JLabel lblFirstName;
    private JLabel lblLastName;
    private JLabel lblEmail;
    private JLabel lblRole;
    private JLabel lblMessage;

    public ProfilePanel(UserController userController, UserDAO userDAO) {
        this.userDAO=userDAO;

        this.userController = userController;
        setLayout(new BorderLayout());

        AuthUser currentUser = Session.getCurrentUser();

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        // CARD
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

        card.setLayout(new BorderLayout());
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(20,20,20,20));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

        // INFO PANEL
        JPanel infoPanel = new JPanel(new GridLayout(0,1));
        infoPanel.setOpaque(false);

        lblFirstName = new JLabel("First Name • " + currentUser.getFirstName());
        lblLastName = new JLabel("Last Name • " + currentUser.getLastName());
        lblEmail = new JLabel("Email • " + currentUser.getEmail());
        lblRole = new JLabel("Role • " + currentUser.getRole());

        JLabel[] labels = {lblFirstName, lblLastName, lblEmail, lblRole};

        for (JLabel lbl : labels) {
            lbl.setForeground(Color.WHITE);
            infoPanel.add(lbl);
            infoPanel.add(Box.createVerticalStrut(6));
        }

        // BUTTONS
        JButton btnUpdate = new JButton("Update");
        JButton btnLogout = new JButton("Logout");

        btnLogout.setBackground(new Color(255,66,69));

        btnUpdate.addActionListener(e -> openUpdateDialog(currentUser));

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if(confirm == JOptionPane.YES_OPTION){
                Session.logout();

                if (SwingUtilities.getWindowAncestor(this) instanceof MainFrame) {
                    ((MainFrame) SwingUtilities.getWindowAncestor(this)).showScreen("START");
                }
            }
        });

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setOpaque(false);

        south.add(btnUpdate);
        south.add(btnLogout);

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(south, BorderLayout.SOUTH);

        container.add(Box.createVerticalStrut(10));
        container.add(card);

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void openUpdateDialog(AuthUser currentUser) {

        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Update Profile", true);

        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(SwingUtilities.getWindowAncestor(this));
        dialog.setLayout(new GridLayout(7,2,10,10));

        ((JComponent) dialog.getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(20,40,0,40)
        );

        JTextField tfFirstName = new JTextField(currentUser.getFirstName());
        JTextField tfLastName = new JTextField(currentUser.getLastName());
        JTextField tfEmail = new JTextField(currentUser.getEmail());

        JTextField tfRole = new JTextField(currentUser.getRole().toString());
        tfRole.setEditable(false);
        tfRole.setFocusable(false);

        JPasswordField tfPassword = new JPasswordField();

        JButton btnSave = new JButton("Save");
        btnSave.setBackground(new Color(0,145,255));

        JButton btnCancel = new JButton("Cancel");

        JLabel lblMessage = new JLabel("");
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);

        // ===== Layout =====

        dialog.add(new JLabel("First Name"));
        dialog.add(tfFirstName);

        dialog.add(new JLabel("Last Name"));
        dialog.add(tfLastName);

        dialog.add(new JLabel("Email"));
        dialog.add(tfEmail);

        dialog.add(new JLabel("Role"));
        dialog.add(tfRole);

        dialog.add(new JLabel("New Password"));
        dialog.add(tfPassword);

        dialog.add(lblMessage);
        dialog.add(new JLabel(""));

        dialog.add(btnSave);
        dialog.add(btnCancel);

        // ===== Actions =====

        btnCancel.addActionListener(e -> dialog.dispose());

        btnSave.addActionListener(e -> {

            try {

                String firstName = tfFirstName.getText().trim();
                String lastName = tfLastName.getText().trim();
                String email = tfEmail.getText().trim();
                String password = new String(tfPassword.getPassword()).trim();

                if (!email.isEmpty() && !email.contains("@")) {
                    lblMessage.setForeground(new Color(255,66,69));
                    lblMessage.setText("Invalid Email.");
                    return;
                }

                User duplicate = userDAO.findByEmail(email);

                if (duplicate != null && !duplicate.getUserId().equals(currentUser.getUserId())) {
                    lblMessage.setForeground(new Color(255,66,69));
                    lblMessage.setText("Email already registered.");
                    return;
                }

                if (!password.isEmpty() && password.length() < 8) {
                    lblMessage.setForeground(new Color(255,66,69));
                    lblMessage.setText("Password must contain at least 8 characters.");
                    return;
                }

                firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
                lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();

                User updatedUser = new User(
                        currentUser.getUserId(),
                        firstName,
                        lastName,
                        email,
                        password,
                        null,
                        0,
                        null
                );

                boolean success = userController.updateUserDetails(currentUser.getUserId(), updatedUser);

                if (success) {

                    JOptionPane.showMessageDialog(dialog,"Profile updated successfully.");

                    lblFirstName.setText("First Name • " + firstName);
                    lblLastName.setText("Last Name • " + lastName);
                    lblEmail.setText("Email • " + email);

                    dialog.dispose();

                } else {

                    lblMessage.setForeground(new Color(255,66,69));
                    lblMessage.setText("Failed to update profile.");

                }

            } catch (Exception ex) {

                ex.printStackTrace();
                lblMessage.setForeground(new Color(255,66,69));
                lblMessage.setText("Unexpected error occurred.");

            }

        });

        dialog.setVisible(true);
    }
}

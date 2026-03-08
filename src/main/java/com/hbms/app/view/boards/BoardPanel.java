package com.hbms.app.view.boards;

import com.hbms.app.controller.UserController;
import com.hbms.app.dao.UserDAO;
import com.hbms.app.model.User;
import com.hbms.app.view.initial.Refreshable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class BoardPanel extends JPanel {
    private UserDAO userDAO;
    private JPanel container;

    public BoardPanel(UserController userController) {
        setLayout(new BorderLayout());
        userDAO = new UserDAO();

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        loadUsers(userController);
    }

    public void loadUsers(UserController userController) {
        container.removeAll();
        List<User> users = userDAO.getAllUsers();
        Collections.reverse(users);

        if (users.isEmpty()) {
            JLabel emptyLabel = new JLabel("No users found.", SwingConstants.CENTER);
            Font currentFont = emptyLabel.getFont();
            Font newFont = currentFont.deriveFont(16f);
            emptyLabel.setFont(newFont);
            container.add(emptyLabel);
            container.revalidate();
            container.repaint();
            return;
        }

        for (User user : users) {
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
            card.setBorder(new EmptyBorder(15,15,15,15));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

            JPanel infoPanel = new JPanel(new GridLayout(0, 1));
            infoPanel.setOpaque(false);

            JLabel userId = new JLabel("User ID • " + user.getUserId());
            JLabel name = new JLabel("Name • " + user.getFirstName() + " " + user.getLastName());
            JLabel email = new JLabel("Email • " + user.getEmail());
            JLabel role = new JLabel("Role • " + user.getRole());

            JLabel[] labels = {userId, name, email, role};

            for (JLabel lbl : labels) {
                lbl.setForeground(Color.WHITE);
                infoPanel.add(lbl);
                infoPanel.add(Box.createVerticalStrut(6));
            }

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);

            JButton btnChangeRole = new JButton("Change Role");
            JButton btnDelete = new JButton("Delete");
            btnDelete.setBackground(new Color(255, 66, 69));

            btnChangeRole.addActionListener(e -> {
                String[] roles = {"CUSTOMER", "SCHEDULER", "MANAGER"};
                String selectedRole = (String) JOptionPane.showInputDialog(this, "Select new role:", "Change Role",
                        JOptionPane.QUESTION_MESSAGE, null, roles, user.getRole().toString());
                if (selectedRole != null) {
                    user.setRole(User.Role.valueOf(selectedRole));
                    userController.updateUserRole(user.getUserId(), new User(null, null, null, null, null, User.Role.valueOf(selectedRole), 0, null));
                    loadUsers(userController);
                }
            });

            btnDelete.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(this, "Delete this user?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    userController.deleteUser(user.getUserId());
                    loadUsers(userController);
                    JOptionPane.showMessageDialog(this, "User deleted.");
                }
            });

            buttonPanel.add(btnChangeRole);
            buttonPanel.add(btnDelete);

            card.add(infoPanel, BorderLayout.CENTER);
            card.add(buttonPanel, BorderLayout.SOUTH);

            container.add(Box.createVerticalStrut(10));
            container.add(card);
        }

        container.revalidate();
        container.repaint();
    }
}

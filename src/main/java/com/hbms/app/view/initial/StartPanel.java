package com.hbms.app.view.initial;

import com.hbms.app.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Map;

public class StartPanel extends JPanel {

    private Image background;

    public StartPanel(MainFrame frame) {
        // Load the background image from resources
        background = new ImageIcon(getClass().getResource("/images/HBMS_Dark.png")).getImage();

        // Use BorderLayout to place buttons at bottom
        setLayout(new BorderLayout());

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // transparent so background shows
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));

        JButton btnLogin = new JButton("Log in");
        JButton btnSignup = new JButton("Sign up");

        // Optional: set preferred size for consistency
        btnLogin.setPreferredSize(new Dimension(120, 40));
        btnLogin.setBackground(new Color(0,145,255));
        btnSignup.setPreferredSize(new Dimension(120, 40));

        Font baseFont = UIManager.getFont("defaultFont");
        Font semiBoldFont = baseFont.deriveFont(
                Map.of(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD, TextAttribute.SIZE, 14f)
        );

        btnLogin.setFont(semiBoldFont);
        btnSignup.setFont(semiBoldFont);

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnSignup);

        // Add button panel to bottom
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        btnLogin.addActionListener(e -> frame.showScreen("LOGIN"));
        btnSignup.addActionListener(e -> frame.showScreen("SIGNUP"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image stretched to panel size
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        // Optional: semi-transparent overlay for better contrast for buttons
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0, 0, 0, 50)); // 50 = alpha for transparency
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}
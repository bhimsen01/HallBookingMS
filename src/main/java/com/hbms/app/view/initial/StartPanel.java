package com.hbms.app.view.initial;

import com.hbms.app.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Map;

public class StartPanel extends JPanel {

    private Image background;

    public StartPanel(MainFrame frame) {
        background = new ImageIcon(getClass().getResource("/images/HBMS_Dark.png")).getImage();

        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));

        JButton btnLogin = new JButton("Log in");
        JButton btnSignup = new JButton("Sign up");

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

        add(buttonPanel, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> frame.showScreen("LOGIN"));
        btnSignup.addActionListener(e -> frame.showScreen("SIGNUP"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}
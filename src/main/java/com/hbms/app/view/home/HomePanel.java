package com.hbms.app.view.home;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class HomePanel extends JPanel {

    public HomePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JLabel lblTitle = new JLabel("Hall Symphony Booking System");
        Font currentFont = lblTitle.getFont();
        Font newFont = currentFont.deriveFont(28f);
        lblTitle.setFont(newFont);
        lblTitle.setForeground(new Color(245, 245, 247));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitle, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        RoundedImagePanel imagePanel = new RoundedImagePanel("/images/HBMS.png", 20);
        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagePanel.setMaximumSize(new Dimension(1000, 800)); // adjust size
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(imagePanel);
        centerPanel.add(Box.createVerticalStrut(20));

        JPanel decorativePanel = new JPanel();
        decorativePanel.setOpaque(false);
        decorativePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));

        decorativePanel.add(createInfoCard("Halls", "Check hall availability and details.", 20));
        decorativePanel.add(createInfoCard("Bookings", "View your upcoming and past bookings.", 20));
        decorativePanel.add(createInfoCard("Support", "Raise issues or contact management.", 20));

        centerPanel.add(decorativePanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createInfoCard(String title, String desc, int radius) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(50,50,100));
                g2.fillRoundRect(0,0,getWidth(),getHeight(), radius, radius);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(200,100));
        card.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDesc = new JLabel("<html><body style='text-align:center'>" + desc + "</body></html>");
        lblDesc.setForeground(new Color(200, 200, 205));
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblTitle);
        card.add(Box.createVerticalStrut(10));
        card.add(lblDesc);

        return card;
    }

    private static class RoundedImagePanel extends JPanel {
        private final Image image;
        private final int radius;

        public RoundedImagePanel(String path, int radius) {
            this.image = new ImageIcon(getClass().getResource("/images/HBMS_Dark.png")).getImage();
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Shape clip = new RoundRectangle2D.Float(0,0,getWidth(),getHeight(), radius, radius);
            g2.setClip(clip);

            g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);

            g2.dispose();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1000,800);
        }
    }
}
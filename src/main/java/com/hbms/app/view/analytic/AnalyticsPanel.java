package com.hbms.app.view.analytic;

import javax.swing.*;
import java.awt.*;

public class AnalyticsPanel extends JPanel {
    public AnalyticsPanel() {
        setLayout(new GridLayout(2,2,20,20));

        add(createBox("Weekly Sales", "RM 5000"));
        add(createBox("Monthly Sales", "RM 20000"));
        add(createBox("Yearly Sales", "RM 150000"));
        add(createBox("Total Bookings", "320"));
    }

    private JPanel createBox(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(new JLabel(title, SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(new JLabel(value, SwingConstants.CENTER), BorderLayout.CENTER);
        return panel;
    }
}

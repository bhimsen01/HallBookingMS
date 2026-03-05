package com.hbms.app.view.home;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    private CardLayout contentLayout;
    private JPanel contentPanel;

    public HomePanel() {
        setLayout(new BorderLayout());
        add(new JLabel("Welcome to Hall Booking System", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}

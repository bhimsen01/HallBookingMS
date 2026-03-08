package com.hbms.app.view.initial;

import com.hbms.app.model.User;
import com.hbms.app.session.Session;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel {
    private final DashboardPanel dashboardPanel;

    public SidebarPanel(DashboardPanel dashboardPanel){
        this.dashboardPanel=dashboardPanel;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(160, 0));

        JPanel navigationPanel=new JPanel();
        navigationPanel.setLayout(new GridLayout(0,1,5,5));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 20, 5));

        java.util.List<String> navButtonsList = new java.util.ArrayList<>();
        java.util.List<String> keysList = new java.util.ArrayList<>();

        navButtonsList.add("Home"); keysList.add("HOME");
        navButtonsList.add("Halls"); keysList.add("HALLS");
        navButtonsList.add("Bookings"); keysList.add("BOOKINGS");
        navButtonsList.add("Issues"); keysList.add("ISSUES");

        if (Session.getCurrentUser().getRole() == User.Role.ADMINISTRATOR || Session.getCurrentUser().getRole() == User.Role.MANAGER) {
            navButtonsList.add("Analytics"); keysList.add("ANALYTICS");
        }

        if(Session.getCurrentUser().getRole()==User.Role.ADMINISTRATOR){
            navButtonsList.add("Board"); keysList.add("BOARD");
        }

        for (int i = 0; i < navButtonsList.size(); i++) {
            JButton button = createButton(navButtonsList.get(i));
            String key = keysList.get(i);
            button.addActionListener(e -> dashboardPanel.showContent(key));
            navigationPanel.add(button);
        }

        JPanel bottomPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 5));

        JButton profile = createButton("Profile");
        profile.addActionListener(e -> dashboardPanel.showContent("PROFILE"));

        JButton settings = createButton("Settings");
        settings.addActionListener(e -> dashboardPanel.showContent("SETTINGS"));

        bottomPanel.add(profile);
        bottomPanel.add(settings);

        add(navigationPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);


    }
    private JButton createButton(String text){
        JButton button=new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(50,50,100));
        return button;
    }
}

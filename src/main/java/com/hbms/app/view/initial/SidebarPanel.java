package com.hbms.app.view.initial;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel {
    private final DashboardPanel dashboardPanel;

    public SidebarPanel(DashboardPanel dashboardPanel){
        this.dashboardPanel=dashboardPanel;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(160, 0));
        setBackground(new Color(30, 30 ,60));

        JPanel navigationPanel=new JPanel();
        navigationPanel.setLayout(new GridLayout(0,1,5,5));
        navigationPanel.setBackground(new Color(30,30,60));

        String[] navButtons={
                "Home",
                "Halls",
                "Bookings",
                "Issues",
                "Analytics",
                "Board"
        };

        String[] keys={
                "HOME",
                "HALLS",
                "BOOKINGS",
                "ISSUES",
                "ANALYTICS",
                "BOARD"
        };

        for (int i =0;i<navButtons.length;i++){
            JButton button=createButton(navButtons[i]);
            String key=keys[i];
            button.addActionListener(e-> dashboardPanel.showContent(key));
            navigationPanel.add(button);
        }

        JPanel bottomPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        bottomPanel.setBackground(new Color(30, 30, 60));

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

package com.hbms.app.view.hall;

import com.hbms.app.controller.HallController;
import com.hbms.app.model.Hall;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class AddHallDialog extends JDialog {

    public AddHallDialog(JFrame parentFrame, HallController hallController, HallsPanel hallsPanel) {

        super(parentFrame, "Add Hall", true);

        setSize(500, 500);
        setLocationRelativeTo(parentFrame);
        setLayout(new GridLayout(9,2,10,10));
        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(20,40,0,40)
        );


        JTextField tfNumber = new JTextField();
        JTextField tfCapacity = new JTextField();
        JTextField tfPrice = new JTextField();
        JTextField tfRemarks = new JTextField();

        JComboBox<Hall.HallType> cbHallType = new JComboBox<>(Hall.HallType.values());

        // ===== Time Spinners =====
        SpinnerDateModel fromModel = new SpinnerDateModel();
        JSpinner spFrom = new JSpinner(fromModel);
        spFrom.setEditor(new JSpinner.DateEditor(spFrom,"HH:mm"));

        SpinnerDateModel untilModel = new SpinnerDateModel();
        JSpinner spUntil = new JSpinner(untilModel);
        spUntil.setEditor(new JSpinner.DateEditor(spUntil,"HH:mm"));

        JButton btnAdd = new JButton("Add Hall");
        btnAdd.setBackground(new Color(0, 145, 255));
        JButton btnCancel = new JButton("Cancel");

        // ===== Layout =====

        add(new JLabel("Hall Number"));
        add(tfNumber);

        add(new JLabel("Hall Type"));
        add(cbHallType);

        add(new JLabel("Capacity"));
        add(tfCapacity);

        add(new JLabel("Price"));
        add(tfPrice);

        add(new JLabel("Available From"));
        add(spFrom);

        add(new JLabel("Available Until"));
        add(spUntil);

        add(new JLabel("Remarks"));
        add(tfRemarks);

        add(btnAdd);
        add(btnCancel);

        // ===== Actions =====

        btnCancel.addActionListener(e -> dispose());

        btnAdd.addActionListener(e -> {
            try {

                int hallNumber = Integer.parseInt(tfNumber.getText().trim());
                int capacity = Integer.parseInt(tfCapacity.getText().trim());
                double price = Double.parseDouble(tfPrice.getText().trim());

                Hall.HallType type = (Hall.HallType) cbHallType.getSelectedItem();

                Date fromDate = (Date) spFrom.getValue();
                LocalTime availableFrom = Instant.ofEpochMilli(fromDate.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalTime();

                Date untilDate = (Date) spUntil.getValue();
                LocalTime availableUntil = Instant.ofEpochMilli(untilDate.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalTime();

                Hall hall = new Hall(
                        hallNumber,
                        type,
                        capacity,
                        price,
                        availableFrom,
                        availableUntil,
                        Hall.HallStatus.AVAILABLE,
                        tfRemarks.getText().trim(),
                        LocalDateTime.now()
                );

                boolean success = hallController.addHall(hall);

                if(success){
                    JOptionPane.showMessageDialog(this,"Hall added successfully!");
                    hallsPanel.loadHalls();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,"Failed to add hall.");
                }

            } catch (Exception ex){
                JOptionPane.showMessageDialog(this,
                        "Invalid input.\nHall Number & Capacity must be integers.\nPrice must be a number.");
            }
        });

        setVisible(true);
    }
}


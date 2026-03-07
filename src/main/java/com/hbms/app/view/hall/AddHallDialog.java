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
    private JLabel lblMessage;

    public AddHallDialog(JFrame parentFrame, HallController hallController, HallsPanel hallsPanel) {

        super(parentFrame, "Add Hall", true);

        setSize(500, 550);
        setLocationRelativeTo(parentFrame);
        setLayout(new GridLayout(11,2,10,10));
        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(20,40,0,40)
        );


        JTextField tfNumber = new JTextField();
        JTextField tfCapacity = new JTextField();
        JTextField tfPrice = new JTextField();
        JTextField tfRemarks = new JTextField();

        JComboBox<Hall.HallType> cbHallType = new JComboBox<>(Hall.HallType.values());
        JComboBox<Hall.HallStatus> cbHallStatus = new JComboBox<>(Hall.HallStatus.values());

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

        add(new JLabel("Status"));
        add(cbHallStatus);

        add(new JLabel("Remarks"));
        add(tfRemarks);

        lblMessage = new JLabel("");
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblMessage);
        add(new JLabel(""));

        add(btnAdd);
        add(btnCancel);

        // ===== Actions =====

        btnCancel.addActionListener(e -> dispose());

        btnAdd.addActionListener(e -> {
            try {
                int hallNumber = Integer.parseInt(tfNumber.getText().trim());
                int capacity = Integer.parseInt(tfCapacity.getText().trim());
                double price = Double.parseDouble(tfPrice.getText().trim());
                String remarks=tfRemarks.getText().trim();

                if (!(hallNumber>0 && hallNumber<10000)){
                    lblMessage.setForeground(new Color(255, 66,69));
                    lblMessage.setText("Invalid Hall Number.");
                }

                if (!(capacity>0 && capacity<=5000)){
                    lblMessage.setForeground(new Color(255, 66,69));
                    lblMessage.setText("Invalid Hall Capacity.");
                }

                if (!(price>0 && price<999999)){
                    lblMessage.setForeground(new Color(255, 66,69));
                    lblMessage.setText("Invalid price.");
                }

                Hall.HallType type = (Hall.HallType) cbHallType.getSelectedItem();

                Hall.HallStatus status = (Hall.HallStatus) cbHallStatus.getSelectedItem();

                Date fromDate = (Date) spFrom.getValue();
                LocalTime availableFrom = Instant.ofEpochMilli(fromDate.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalTime();

                Date untilDate = (Date) spUntil.getValue();
                LocalTime availableUntil = Instant.ofEpochMilli(untilDate.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalTime();

                if (availableUntil.isBefore(availableFrom)) {
                    lblMessage.setForeground(new Color(255,66,69));
                    lblMessage.setText("Available Until not after Available From.");
                }

                Hall hall = new Hall(
                        hallNumber,
                        type,
                        capacity,
                        price,
                        availableFrom,
                        availableUntil,
                        status,
                        remarks,
                        LocalDateTime.now()
                );

                boolean success = hallController.addHall(hall);

                if(success){
                    JOptionPane.showMessageDialog(this,"Hall added successfully.");
                    hallsPanel.loadHalls();
                    dispose();
                } else {
                    lblMessage.setForeground(new Color(255,66,69));
                    lblMessage.setText("Failed to add hall.");
                }

            } catch (Exception ex){
                lblMessage.setForeground(new Color(255,66,69));
                lblMessage.setText("Invalid input format.");
            }
        });

        setVisible(true);
    }
}


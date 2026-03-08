package com.hbms.app.view.halls;

import com.hbms.app.controller.HallController;
import com.hbms.app.model.Hall;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class EditHallDialog extends JDialog {
    private JLabel lblMessage;

    public EditHallDialog(JFrame parentFrame, HallController hallController, HallsPanel hallsPanel, Hall hall) {

        super(parentFrame, "Edit Hall", true);

        setSize(500, 550);
        setLocationRelativeTo(parentFrame);
        setLayout(new GridLayout(11,2,10,10));

        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(20,40,0,40)
        );

        JTextField tfNumber = new JTextField(String.valueOf(hall.getHallNumber()));
        tfNumber.setEditable(false);
        tfNumber.setFocusable(false);

        JComboBox<Hall.HallType> cbHallType = new JComboBox<>(Hall.HallType.values());
        cbHallType.setSelectedItem(hall.getHallType());

        JComboBox<Hall.HallStatus> cbHallStatus = new JComboBox<>(Hall.HallStatus.values());
        cbHallStatus.setSelectedItem(hall.getHallStatus());

        JTextField tfCapacity = new JTextField(String.valueOf(hall.getHallCapacity()));
        JTextField tfPrice = new JTextField(String.format("%.2f", hall.getHallPrice()));
        JTextField tfRemarks = new JTextField(hall.getHallRemarks());

        SpinnerDateModel fromModel = new SpinnerDateModel();
        JSpinner spFrom = new JSpinner(fromModel);
        spFrom.setEditor(new JSpinner.DateEditor(spFrom, "HH:mm"));
        spFrom.setValue(Date.from(hall.getHallAvailableFrom().atDate(LocalDate.now())
                .atZone(ZoneId.systemDefault()).toInstant()));

        SpinnerDateModel untilModel = new SpinnerDateModel();
        JSpinner spUntil = new JSpinner(untilModel);
        spUntil.setEditor(new JSpinner.DateEditor(spUntil, "HH:mm"));
        spUntil.setValue(Date.from(hall.getHallAvailableUntil().atDate(LocalDate.now())
                .atZone(ZoneId.systemDefault()).toInstant()));

        JButton btnSave = new JButton("Save");
        btnSave.setBackground(new Color(0, 145, 255));
        JButton btnCancel = new JButton("Cancel");

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



        add(btnSave);
        add(btnCancel);

        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> {
            try {
                Hall.HallType type = (Hall.HallType) cbHallType.getSelectedItem();
                int capacity = Integer.parseInt(tfCapacity.getText().trim());
                double price = Double.parseDouble(tfPrice.getText().trim());
                Date fromDate = (Date) spFrom.getValue();
                LocalTime availableFrom = Instant.ofEpochMilli(fromDate.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalTime();
                Date untilDate = (Date) spUntil.getValue();
                LocalTime availableUntil = Instant.ofEpochMilli(untilDate.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalTime();
                Hall.HallStatus status = (Hall.HallStatus) cbHallStatus.getSelectedItem();
                String remarks = tfRemarks.getText().trim();

                if (!(capacity>0 && capacity<=5000)){
                    lblMessage.setForeground(new Color(255, 66,69));
                    lblMessage.setText("Invalid Hall Capacity.");
                }

                if (!(price>0 && price<999999)){
                    lblMessage.setForeground(new Color(255, 66,69));
                    lblMessage.setText("Invalid price.");
                }

                if (availableUntil.isBefore(availableFrom)) {
                    lblMessage.setForeground(new Color(255,66,69));
                    lblMessage.setText("Available Until not after Available From.");
                }

                Hall updatedHall = new Hall();
                updatedHall.setHallType(type);
                updatedHall.setHallCapacity(capacity);
                updatedHall.setHallPrice(price);
                updatedHall.setHallAvailableFrom(availableFrom);
                updatedHall.setHallAvailableUntil(availableUntil);
                updatedHall.setHallStatus(status);
                updatedHall.setHallRemarks(remarks);

                boolean success = hallController.editHall(hall.getHallNumber(), updatedHall);

                if(success){
                    JOptionPane.showMessageDialog(this,"Hall edited successfully.");
                    hallsPanel.loadHalls();
                    dispose();
                } else {
                    lblMessage.setForeground(new Color(255,66,69));
                    lblMessage.setText("Failed to edit hall.");
                }

            } catch (Exception ex){
                lblMessage.setForeground(new Color(255,66,69));
                lblMessage.setText("Invalid input format.");
            }
        });

        setVisible(true);
    }
}


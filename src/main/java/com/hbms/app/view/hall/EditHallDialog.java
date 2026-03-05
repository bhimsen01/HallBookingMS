package com.hbms.app.view.hall;

import com.hbms.app.controller.HallController;
import com.hbms.app.model.Hall;

import javax.swing.*;
import java.awt.*;

public class EditHallDialog extends JDialog {

    private final HallController hallController;
    private final HallsPanel hallsPanel;
    private final Hall hall;

    public EditHallDialog(JFrame parentFrame, HallController hallController, HallsPanel hallsPanel, Hall hall) {
        super(parentFrame, "Edit Hall #" + hall.getHallNumber(), true);

        this.hallController = hallController;
        this.hallsPanel = hallsPanel;
        this.hall = hall;

        setSize(400, 300);
        setLocationRelativeTo(parentFrame);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Hall Number (read-only)
        JTextField tfNumber = new JTextField(String.valueOf(hall.getHallNumber()));
        tfNumber.setEditable(false);

        // Hall Type (read-only)
        JTextField tfType = new JTextField(hall.getHallType().toString());
        tfType.setEditable(false);

        // Price
        JTextField tfPrice = new JTextField(String.valueOf(hall.getHallPrice()));

        // Capacity
        JTextField tfCapacity = new JTextField(String.valueOf(hall.getHallCapacity()));

        // Status dropdown
        JComboBox<Hall.HallStatus> cbStatus = new JComboBox<>(Hall.HallStatus.values());
        cbStatus.setSelectedItem(hall.getHallStatus());

        // Remarks
        JTextField tfRemarks = new JTextField(hall.getHallRemarks());

        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        add(new JLabel("Hall Number:"));
        add(tfNumber);

        add(new JLabel("Hall Type:"));
        add(tfType);

        add(new JLabel("Price:"));
        add(tfPrice);

        add(new JLabel("Capacity:"));
        add(tfCapacity);

        add(new JLabel("Status:"));
        add(cbStatus);

        add(new JLabel("Remarks:"));
        add(tfRemarks);

        add(btnSave);
        add(btnCancel);

        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> {
            try {
                double price = Double.parseDouble(tfPrice.getText().trim());
                int capacity = Integer.parseInt(tfCapacity.getText().trim());
                Hall.HallStatus status = (Hall.HallStatus) cbStatus.getSelectedItem();
                String remarks = tfRemarks.getText().trim();

                Hall updatedHall = new Hall();
                updatedHall.setHallPrice(price);
                updatedHall.setHallCapacity(capacity);
                updatedHall.setHallStatus(status);
                updatedHall.setHallRemarks(remarks);

                boolean msg = hallController.editHall(hall.getHallNumber(), updatedHall);
                JOptionPane.showMessageDialog(this, msg);
                hallsPanel.loadHalls();
                dispose();

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for price and capacity.");
            }
        });

        setVisible(true);
    }
}

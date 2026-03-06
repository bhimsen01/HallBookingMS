package com.hbms.app.view.hall;

import com.hbms.app.controller.HallController;
import com.hbms.app.model.Hall;

import javax.swing.*;
import java.awt.*;

public class EditHallDialog extends JDialog {

    public EditHallDialog(JFrame parentFrame, HallController hallController, HallsPanel hallsPanel, Hall hall) {

        super(parentFrame, "Edit Hall #" + hall.getHallNumber(), true);

        setSize(500, 500);
        setLocationRelativeTo(parentFrame);
        setLayout(new GridLayout(9,2,10,10));

        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(20,40,0,40)
        );

        // Fields
        JTextField tfNumber = new JTextField(String.valueOf(hall.getHallNumber()));
        tfNumber.setEditable(false);

        JTextField tfType = new JTextField(hall.getHallType().toString());
        tfType.setEditable(false);

        JTextField tfCapacity = new JTextField(String.valueOf(hall.getHallCapacity()));
        JTextField tfPrice = new JTextField(String.valueOf(hall.getHallPrice()));
        JTextField tfRemarks = new JTextField(hall.getHallRemarks());

        JComboBox<Hall.HallStatus> cbStatus = new JComboBox<>(Hall.HallStatus.values());
        cbStatus.setSelectedItem(hall.getHallStatus());

        JButton btnSave = new JButton("Save");
        btnSave.setBackground(new Color(0, 145, 255));
        JButton btnCancel = new JButton("Cancel");

        // ===== Layout =====

        add(new JLabel("Hall Number"));
        add(tfNumber);

        add(new JLabel("Hall Type"));
        add(tfType);

        add(new JLabel("Capacity"));
        add(tfCapacity);

        add(new JLabel("Price"));
        add(tfPrice);

        add(new JLabel("Status"));
        add(cbStatus);

        add(new JLabel("Remarks"));
        add(tfRemarks);

        add(new JLabel(""));
        add(new JLabel(""));

        add(btnSave);
        add(btnCancel);

        // ===== Actions =====

        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> {
            try {

                int capacity = Integer.parseInt(tfCapacity.getText().trim());
                double price = Double.parseDouble(tfPrice.getText().trim());
                Hall.HallStatus status = (Hall.HallStatus) cbStatus.getSelectedItem();
                String remarks = tfRemarks.getText().trim();

                Hall updatedHall = new Hall();
                updatedHall.setHallCapacity(capacity);
                updatedHall.setHallPrice(price);
                updatedHall.setHallStatus(status);
                updatedHall.setHallRemarks(remarks);

                boolean success = hallController.editHall(hall.getHallNumber(), updatedHall);

                if(success){
                    JOptionPane.showMessageDialog(this,"Hall updated successfully!");
                    hallsPanel.loadHalls();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,"Failed to update hall.");
                }

            } catch (Exception ex){
                JOptionPane.showMessageDialog(this,
                        "Invalid input.\nCapacity must be integer.\nPrice must be a number.");
            }
        });

        setVisible(true);
    }
}


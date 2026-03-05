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

        setSize(500, 600);
        setLocationRelativeTo(parentFrame);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));

        Font labelFont = new Font("Inter", Font.PLAIN, 14);

        // Hall Number (read-only)
        JTextField tfNumber = new JTextField(String.valueOf(hall.getHallNumber()));
        tfNumber.setEditable(false);
        tfNumber.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));

        // Hall Type (read-only)
        JTextField tfType = new JTextField(hall.getHallType().toString());
        tfType.setEditable(false);
        tfType.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));

        // Price
        JTextField tfPrice = new JTextField(String.valueOf(hall.getHallPrice()));
        tfPrice.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));

        // Capacity
        JTextField tfCapacity = new JTextField(String.valueOf(hall.getHallCapacity()));
        tfCapacity.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));

        // Status dropdown
        JComboBox<Hall.HallStatus> cbStatus = new JComboBox<>(Hall.HallStatus.values());
        cbStatus.setSelectedItem(hall.getHallStatus());
        cbStatus.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));

        // Remarks
        JTextField tfRemarks = new JTextField(hall.getHallRemarks());
        tfRemarks.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));

        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        btnSave.setPreferredSize(new Dimension(120,40));
        btnCancel.setPreferredSize(new Dimension(120,40));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // ===== Build Form =====
        addField(formPanel,"Hall Number",tfNumber,labelFont);
        addField(formPanel,"Hall Type",tfType,labelFont);
        addField(formPanel,"Price",tfPrice,labelFont);
        addField(formPanel,"Capacity",tfCapacity,labelFont);
        addField(formPanel,"Status",cbStatus,labelFont);
        addField(formPanel,"Remarks",tfRemarks,labelFont);

        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(buttonPanel);

        add(formPanel);

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

    private void addField(JPanel panel, String label, Component field, Font font) {

        JLabel lbl = new JLabel(label);
        lbl.setFont(font);
        lbl.setHorizontalAlignment(SwingConstants.LEFT);
        lbl.setMaximumSize(new Dimension(Integer.MAX_VALUE, lbl.getPreferredSize().height));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);


        panel.add(lbl);
        panel.add(Box.createVerticalStrut(4));
        panel.add(field);
        panel.add(Box.createVerticalStrut(12));
    }
}

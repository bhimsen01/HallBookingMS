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

    private final HallController hallController;
    private final HallsPanel hallsPanel;

    public AddHallDialog(JFrame parentFrame, HallController hallController, HallsPanel hallsPanel) {
        super(parentFrame, "Add Hall", true);

        this.hallController = hallController;
        this.hallsPanel = hallsPanel;

        setSize(400, 400);
        setLocationRelativeTo(parentFrame);
        setLayout(new GridLayout(9, 2, 5, 5));

        // Components
        JTextField tfNumber = new JTextField();
        JRadioButton rbAuditorium = new JRadioButton("AUDITORIUM", true);
        JRadioButton rbBoardroom = new JRadioButton("BOARDROOM");
        JRadioButton rbBanquet = new JRadioButton("BANQUET");

        ButtonGroup bgType = new ButtonGroup();
        bgType.add(rbAuditorium);
        bgType.add(rbBoardroom);
        bgType.add(rbBanquet);

        JTextField tfCapacity = new JTextField();
        JTextField tfPrice = new JTextField();
        JTextField tfRemarks = new JTextField();

        // Use JSpinner for time input with SpinnerDateModel and modern conversion
        SpinnerDateModel fromModel = new SpinnerDateModel();
        JSpinner spFrom = new JSpinner(fromModel);
        spFrom.setEditor(new JSpinner.DateEditor(spFrom, "HH:mm"));

        SpinnerDateModel untilModel = new SpinnerDateModel();
        JSpinner spUntil = new JSpinner(untilModel);
        spUntil.setEditor(new JSpinner.DateEditor(spUntil, "HH:mm"));

        JButton btnAdd = new JButton("Add");
        JButton btnCancel = new JButton("Cancel");

        add(new JLabel("Hall Number:"));
        add(tfNumber);

        add(new JLabel("Hall Type:"));

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.add(rbAuditorium);
        radioPanel.add(rbBoardroom);
        radioPanel.add(rbBanquet);
        add(radioPanel);

        add(new JLabel("Capacity:"));
        add(tfCapacity);

        add(new JLabel("Price:"));
        add(tfPrice);

        add(new JLabel("Available From (HH:mm):"));
        add(spFrom);

        add(new JLabel("Available Until (HH:mm):"));
        add(spUntil);

        add(new JLabel("Remarks:"));
        add(tfRemarks);

        add(btnAdd);
        add(btnCancel);

        btnCancel.addActionListener(e -> dispose());

        btnAdd.addActionListener(e -> {
            try {
                String numberStr = tfNumber.getText().trim();
                String capacityStr = tfCapacity.getText().trim();
                String priceStr = tfPrice.getText().trim();

                System.out.println("Debug Inputs:");
                System.out.println("Hall Number: '" + numberStr + "'");
                System.out.println("Capacity: '" + capacityStr + "'");
                System.out.println("Price: '" + priceStr + "'");

                int hallNumber = Integer.parseInt(numberStr);
                int capacity = Integer.parseInt(capacityStr);
                double price = Double.parseDouble(priceStr);

                Hall.HallType type =
                        rbAuditorium.isSelected() ? Hall.HallType.AUDITORIUM :
                                rbBoardroom.isSelected() ? Hall.HallType.BOARDROOM :
                                        Hall.HallType.BANQUET;

                // Convert spinner Date to LocalTime using modern API
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

                boolean message = hallController.addHall(hall);

                JOptionPane.showMessageDialog(this, message);

                if (message) {
                    hallsPanel.loadHalls();
                    dispose();
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this,
                        "Invalid input format for number, capacity or price.\nPlease enter valid numbers.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Invalid input. Please check all fields.\n" +
                                "Hall Number, Capacity must be integers.\n" +
                                "Price must be a number.\n" +
                                "Time will be selected from dropdown.");
            }
        });

        setVisible(true);
    }
}
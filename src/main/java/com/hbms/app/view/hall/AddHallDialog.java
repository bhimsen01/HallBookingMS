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

        setSize(500, 600);
        setLocationRelativeTo(parentFrame);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));

        Font labelFont = new Font("Inter", Font.PLAIN, 14);

        JTextField tfNumber = new JTextField();
        JTextField tfCapacity = new JTextField();
        JTextField tfPrice = new JTextField();
        JTextField tfRemarks = new JTextField();

        tfNumber.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
        tfCapacity.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
        tfPrice.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
        tfRemarks.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));

        // ===== Hall Type =====
        JRadioButton rbAuditorium = new JRadioButton("AUDITORIUM", true);
        JRadioButton rbBoardroom = new JRadioButton("BOARDROOM");
        JRadioButton rbBanquet = new JRadioButton("BANQUET");

        ButtonGroup bgType = new ButtonGroup();
        bgType.add(rbAuditorium);
        bgType.add(rbBoardroom);
        bgType.add(rbBanquet);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
        radioPanel.add(rbAuditorium);
        radioPanel.add(rbBoardroom);
        radioPanel.add(rbBanquet);

        // ===== Time Spinners =====
        SpinnerDateModel fromModel = new SpinnerDateModel();
        JSpinner spFrom = new JSpinner(fromModel);
        spFrom.setEditor(new JSpinner.DateEditor(spFrom,"HH:mm"));
        spFrom.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));

        SpinnerDateModel untilModel = new SpinnerDateModel();
        JSpinner spUntil = new JSpinner(untilModel);
        spUntil.setEditor(new JSpinner.DateEditor(spUntil,"HH:mm"));
        spUntil.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));

        JButton btnAdd = new JButton("Add Hall");
        JButton btnCancel = new JButton("Cancel");

        btnAdd.setPreferredSize(new Dimension(120,40));
        btnCancel.setPreferredSize(new Dimension(120,40));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);

        // ===== Build Form =====
        addField(formPanel,"Hall Number",tfNumber,labelFont);
        addField(formPanel,"Hall Type",radioPanel,labelFont);
        addField(formPanel,"Capacity",tfCapacity,labelFont);
        addField(formPanel,"Price",tfPrice,labelFont);
        addField(formPanel,"Available From",spFrom,labelFont);
        addField(formPanel,"Available Until",spUntil,labelFont);
        addField(formPanel,"Remarks",tfRemarks,labelFont);

        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(buttonPanel);

        add(formPanel);

        btnCancel.addActionListener(e -> dispose());

        btnAdd.addActionListener(e -> {
            try {

                int hallNumber = Integer.parseInt(tfNumber.getText().trim());
                int capacity = Integer.parseInt(tfCapacity.getText().trim());
                double price = Double.parseDouble(tfPrice.getText().trim());

                Hall.HallType type =
                        rbAuditorium.isSelected() ? Hall.HallType.AUDITORIUM :
                                rbBoardroom.isSelected() ? Hall.HallType.BOARDROOM :
                                        Hall.HallType.BANQUET;

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

                JOptionPane.showMessageDialog(this,message);

                if(message){
                    hallsPanel.loadHalls();
                    dispose();
                }

            } catch (Exception ex){
                JOptionPane.showMessageDialog(this,
                        "Invalid input.\nHall Number & Capacity must be integers.\nPrice must be a number.");
            }
        });

        setVisible(true);
    }

    private void addField(JPanel panel,String label,Component field,Font font){
        JLabel lbl = new JLabel(label);
        lbl.setFont(font);

        panel.add(lbl);
        panel.add(field);
        panel.add(Box.createVerticalStrut(10));
    }
}
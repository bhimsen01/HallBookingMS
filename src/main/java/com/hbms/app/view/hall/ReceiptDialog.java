package com.hbms.app.view.hall;

import com.hbms.app.model.Receipt;
import com.hbms.app.model.User;
import com.hbms.app.model.Booking;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class ReceiptDialog extends JDialog {

    public ReceiptDialog(JFrame parentFrame, Receipt receipt, Booking booking, User user){
        super(parentFrame, "Booking Successful", true);
        setSize(550, 500);
        setLocationRelativeTo(parentFrame);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Font labelFont = new Font("Inter", Font.PLAIN, 14);
        Font titleFont = new Font("Inter", Font.BOLD, 16);

        JLabel lblTitle = new JLabel("Booking Successful!");
        lblTitle.setFont(titleFont);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(lblTitle);
        formPanel.add(Box.createVerticalStrut(15));

        addField(formPanel, "Receipt ID", new JLabel(receipt.getReceptID()), labelFont);
        addField(formPanel, "Hall Number", new JLabel(String.valueOf(booking.getHallNumber())), labelFont);
        addField(formPanel, "Hall Type", new JLabel(booking.getHallType().toString()), labelFont);
        addField(formPanel, "Booking Date", new JLabel(booking.getBookingDate().toString()), labelFont);
        addField(formPanel, "Booked From", new JLabel(booking.getBookingFrom().toString()), labelFont);
        addField(formPanel, "Booked Until", new JLabel(booking.getBookingUntil().toString()), labelFont);
        addField(formPanel, "Booked By", new JLabel(user.getFirstName() + " " + user.getLastName()), labelFont);
        addField(formPanel, "Receipt Generated", new JLabel(receipt.getReceiptCreatedAt().format(dtf)), labelFont);

        JButton btnClose = new JButton("Okay");
        btnClose.setPreferredSize(new Dimension(120,40));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnClose);

        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(buttonPanel);

        add(formPanel);

        btnClose.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void addField(JPanel panel, String label, JLabel valueLabel, Font font) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(font);
        lbl.setForeground(Color.BLACK);

        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.add(lbl, BorderLayout.WEST);
        row.add(valueLabel, BorderLayout.EAST);
        valueLabel.setFont(font);
        valueLabel.setForeground(new Color(50,50,100));

        panel.add(row);
        panel.add(Box.createVerticalStrut(8));
    }
}
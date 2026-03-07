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
        setSize(450, 500);
        setLocationRelativeTo(parentFrame);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        JLabel lblTitle = new JLabel("Booking Successful");
        Font currentFont = lblTitle.getFont();
        Font newFont = currentFont.deriveFont(16f);
        lblTitle.setFont(newFont);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(lblTitle);
        formPanel.add(Box.createVerticalStrut(15));

        addField(formPanel, "Receipt ID", new JLabel(receipt.getReceptID()));
        addField(formPanel, "Hall Number", new JLabel(String.valueOf(booking.getHallNumber())));
        addField(formPanel, "Hall Type", new JLabel(booking.getHallType().toString()));
        addField(formPanel, "Booking Date", new JLabel(booking.getBookingDate().toString()));
        addField(formPanel, "Booked From", new JLabel(booking.getBookingFrom().toString()));
        addField(formPanel, "Booked Until", new JLabel(booking.getBookingUntil().toString()));
        addField(formPanel, "Booked By", new JLabel(user.getFirstName() + " " + user.getLastName()));
        addField(formPanel, "Email", new JLabel(user.getEmail()));
        addField(formPanel, "Receipt Generated", new JLabel(receipt.getReceiptCreatedAt().format(dtf)));

        JButton btnClose = new JButton("Okay");
        btnClose.setBackground(new Color(0, 145, 255));
        btnClose.setPreferredSize(new Dimension(120,40));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnClose);

        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(buttonPanel);

        add(formPanel);

        btnClose.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void addField(JPanel panel, String label, JLabel valueLabel) {
        JLabel lbl = new JLabel(label);

        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.add(lbl, BorderLayout.WEST);
        row.add(valueLabel, BorderLayout.EAST);


        panel.add(row);
        panel.add(Box.createVerticalStrut(8));
    }
}
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
        setSize(450, 350);
        setLocationRelativeTo(parentFrame);
        setLayout(new GridLayout(0,1,10,5));
        setBackground(Color.WHITE);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        add(new JLabel("Booking Successful!", SwingConstants.CENTER));
        add(new JLabel("Receipt ID: " + receipt.getReceptID()));
        add(new JLabel("Hall Number: " + booking.getHallNumber()));
        add(new JLabel("Hall Type: " + booking.getHallType()));
        add(new JLabel("Booking Date: " + booking.getBookingDate()));
        add(new JLabel("Booked From: " + booking.getBookingFrom()));
        add(new JLabel("Booked Until: " + booking.getBookingUntil()));
        add(new JLabel("Booked By: " + user.getFirstName() + " " + user.getLastName()));
        add(new JLabel("Receipt Generated At: " + receipt.getReceiptCreatedAt().format(dtf)));

        JButton btnClose = new JButton("Okay");
        btnClose.addActionListener(e -> dispose());
        add(btnClose);

        setVisible(true);
    }
}
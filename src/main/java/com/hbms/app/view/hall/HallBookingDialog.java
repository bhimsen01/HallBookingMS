package com.hbms.app.view.hall;

import com.hbms.app.controller.BookingController;
import com.hbms.app.model.Hall;

import javax.swing.*;
import java.awt.*;

public class HallBookingDialog extends JDialog {
    private JLabel lblMessage;

    public HallBookingDialog(JFrame parentFrame, Hall hall, BookingController bookingController, Runnable onSuccess) {
        super(parentFrame, "Book Hall #" + hall.getHallNumber(), true); // true = modal

        setSize(350, 300);
        setLocationRelativeTo(parentFrame);
        setLayout(new GridLayout(6,2,10,10));

        JTextField tfDate = new JTextField("2025-03-05");
        JTextField tfFrom = new JTextField("09:00");
        JTextField tfUntil = new JTextField("12:00");
        JTextField tfAmount = new JTextField(String.valueOf(hall.getHallPrice()));
        tfAmount.setEditable(false);

        JButton btnBook = new JButton("Book");
        JButton btnCancel = new JButton("Cancel");

        lblMessage=new JLabel("");
        lblMessage.setForeground(Color.RED);

        add(new JLabel("Date (YYYY-MM-DD):"));
        add(tfDate);

        add(new JLabel("From (HH:MM):"));
        add(tfFrom);

        add(new JLabel("Until (HH:MM):"));
        add(tfUntil);

        add(new JLabel("Amount:"));
        add(tfAmount);

        add(lblMessage);
        add(new JLabel(""));

        add(btnBook);
        add(btnCancel);

        btnCancel.addActionListener(e -> dispose());

        btnBook.addActionListener(e -> {
            boolean message = bookingController.addBooking(
                    hall.getHallType().name(),
                    String.valueOf(hall.getHallNumber()),
                    tfDate.getText().trim(),
                    tfFrom.getText().trim(),
                    tfUntil.getText().trim(),
                    hall.getHallPrice()
            );

            JOptionPane.showMessageDialog(this, message);

            if (message) {
                if (onSuccess!=null) {
                    try { onSuccess.run(); } catch (Exception ex) { ex.printStackTrace(); }
                }
                dispose();
            }
        });

        setVisible(true);
    }
}
package com.hbms.app.view.hall;

import com.hbms.app.controller.BookingController;
import com.hbms.app.controller.ReceiptController;
import com.hbms.app.dao.BookingDAO;
import com.hbms.app.dao.UserDAO;
import com.hbms.app.model.Booking;
import com.hbms.app.model.Hall;
import com.hbms.app.model.User;
import com.hbms.app.session.Session;
import com.hbms.app.session.Session;

import javax.swing.*;
import java.awt.*;

public class HallBookingDialog extends JDialog {
    private JLabel lblMessage;

    public HallBookingDialog(JFrame parentFrame, Hall hall, BookingController bookingController, ReceiptController receiptController, Runnable onSuccess) {
        super(parentFrame, "Book Hall " + hall.getHallNumber(), true); // true = modal

        setSize(350, 300);
        setLocationRelativeTo(parentFrame);
        setLayout(new GridLayout(6,2,10,10));
        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(20,40,20,40)
        );

        JTextField tfDate = new JTextField("2025-03-05");
        JTextField tfFrom = new JTextField("09:00");
        JTextField tfUntil = new JTextField("12:00");
        JTextField tfAmount = new JTextField(String.valueOf(hall.getHallPrice()));
        tfAmount.setEditable(false);

        JButton btnBook = new JButton("Book");
        btnBook.setBackground(new Color(0, 145, 255));
        JButton btnCancel = new JButton("Cancel");

        lblMessage=new JLabel("");
        lblMessage.setForeground(new Color(255, 69,69));

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
            String bookingId = bookingController.addBooking(
                    hall.getHallType().name(),
                    String.valueOf(hall.getHallNumber()),
                    tfDate.getText().trim(),
                    tfFrom.getText().trim(),
                    tfUntil.getText().trim(),
                    hall.getHallPrice()
            );

            if (bookingId != null) {
                // Get the receipt for this specific booking
                com.hbms.app.model.Receipt receipt = receiptController.getReceiptByBookingId(bookingId);

                if(receipt != null){
                    BookingDAO bookingDAO = new BookingDAO();
                    Booking booking = bookingDAO.findById(bookingId);

                    UserDAO userDAO = new UserDAO();
                    User user = userDAO.findById(Session.getCurrentUser().getUserId());

                    if(booking != null && user != null){
                        new ReceiptDialog(parentFrame, receipt, booking, user);
                    }
                }

                if (onSuccess!=null) {
                    try { onSuccess.run(); } catch (Exception ex) { ex.printStackTrace(); }
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Booking failed. Please try again.");
            }
        });

        setVisible(true);
    }
}
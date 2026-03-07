package com.hbms.app.view.hall;

import com.hbms.app.controller.BookingController;
import com.hbms.app.controller.ReceiptController;
import com.hbms.app.dao.BookingDAO;
import com.hbms.app.dao.UserDAO;
import com.hbms.app.model.Booking;
import com.hbms.app.model.Hall;
import com.hbms.app.model.Receipt;
import com.hbms.app.model.User;
import com.hbms.app.session.Session;
import com.hbms.app.session.Session;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.util.Date;

public class HallBookingDialog extends JDialog {
    private JLabel lblMessage;

    public HallBookingDialog(JFrame parentFrame, Hall hall, BookingController bookingController, ReceiptController receiptController, Runnable onSuccess) {
        super(parentFrame, "Book Hall " + hall.getHallNumber(), true); // true = modal

        setSize(450, 400);
        setLocationRelativeTo(parentFrame);
        setLayout(new GridLayout(7,2,10,10));
        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(20,40,20,40)
        );

        JTextField tfDate = new JTextField(String.valueOf(LocalDate.now()));

        SpinnerDateModel fromModel = new SpinnerDateModel();
        JSpinner spFrom = new JSpinner(fromModel);
        spFrom.setEditor(new JSpinner.DateEditor(spFrom, "HH:mm"));
        spFrom.setValue(Date.from(hall.getHallAvailableFrom().atDate(LocalDate.now())
                .atZone(ZoneId.systemDefault()).toInstant()));

        SpinnerDateModel untilModel = new SpinnerDateModel();
        JSpinner spUntil = new JSpinner(untilModel);
        spUntil.setEditor(new JSpinner.DateEditor(spUntil, "HH:mm"));
        spUntil.setValue(Date.from(hall.getHallAvailableUntil().atDate(LocalDate.now())
                .atZone(ZoneId.systemDefault()).toInstant()));


        JTextField tfAmount = new JTextField(String.valueOf(hall.getHallPrice()));
        tfAmount.setEditable(false);
        tfAmount.setFocusable(false);

        JButton btnBook = new JButton("Book");
        btnBook.setBackground(new Color(0, 145, 255));
        JButton btnCancel = new JButton("Cancel");

        add(new JLabel("Date (YYYY-MM-DD)"));
        add(tfDate);

        add(new JLabel("From (HH:MM)"));
        add(spFrom);

        add(new JLabel("Until (HH:MM)"));
        add(spUntil);

        add(new JLabel("Amount"));
        add(tfAmount);

        lblMessage = new JLabel("");
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblMessage);
        add(new JLabel(""));

        add(btnBook);
        add(btnCancel);

        btnCancel.addActionListener(e -> dispose());

        btnBook.addActionListener(e -> {
            Date fromDate = (Date) spFrom.getValue();
            LocalTime bookFrom = Instant.ofEpochMilli(fromDate.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime();

            Date untilDate = (Date) spUntil.getValue();
            LocalTime bookUntil = Instant.ofEpochMilli(untilDate.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime();

            if (tfDate.getText().trim().isEmpty()) {
                lblMessage.setForeground(new Color(255,66,69));
                lblMessage.setText("Empty date field.");
            }

            LocalDate bookingDate;
            try {
                bookingDate = LocalDate.parse(tfDate.getText().trim());
            } catch (Exception ex) {
                lblMessage.setForeground(new Color(255,66,69));
                lblMessage.setText("Invalid date format.");
                return;
            }

            if (bookFrom.isBefore(hall.getHallAvailableFrom())) {
                lblMessage.setForeground(new Color(255,66,69));
                lblMessage.setText("Booking From before Available From.");
            }

            if (bookUntil.isAfter(hall.getHallAvailableUntil())) {
                lblMessage.setForeground(new Color(255,66,69));
                lblMessage.setText("Booking Until after Available Until.");
            }

            String bookingId = bookingController.addBooking(
                    hall.getHallType().name(),
                    String.valueOf(hall.getHallNumber()),
                    String.valueOf(bookingDate),
                    String.valueOf(bookFrom),
                    String.valueOf(bookUntil),
                    hall.getHallPrice()
            );

            if (bookingId != null) {
                lblMessage.setForeground(new Color(48,209,88));
                lblMessage.setText("Hall booked successfully.");
                Receipt receipt = receiptController.getReceiptByBookingId(bookingId);

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
                lblMessage.setForeground(new Color(255, 66,69));
                lblMessage.setText("Failed to book hall.");
            }
        });

        setVisible(true);
    }
}
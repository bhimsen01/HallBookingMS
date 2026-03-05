package com.hbms.app.view.booking;

import com.hbms.app.controller.BookingController;
import com.hbms.app.controller.IssueController;
import com.hbms.app.dao.BookingDAO;
import com.hbms.app.model.Booking;
import com.hbms.app.session.Session;
import com.hbms.app.view.issue.IssueDialog;
import com.hbms.app.view.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingsPanel extends JPanel {

    private final BookingDAO bookingDAO;

    public BookingsPanel(JFrame parentFrame, BookingController bookingController, IssueController issueController) {

        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        bookingDAO = new BookingDAO();

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(240, 240, 240));

        // 🔥 Get current logged-in user ID
        String userId = Session.getCurrentUser().getUserId();

        // 🔥 Get only bookings of this user
        List<Booking> myBookings = bookingDAO.getByUserId(userId);
        myBookings = new ArrayList<>(myBookings); // make mutable
        Collections.reverse(myBookings);

        if (myBookings.isEmpty()) {
            JLabel emptyLabel = new JLabel("No bookings found.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.BOLD, 16));
            add(emptyLabel, BorderLayout.CENTER);
            return;
        }

        for (Booking booking : myBookings) {


            JPanel card = new JPanel(new BorderLayout());
            card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            card.setBackground(Color.WHITE);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
            card.setPreferredSize(new Dimension(500, 200));

            JPanel infoPanel = new JPanel(new GridLayout(0,1));
            infoPanel.setBackground(Color.WHITE);
            infoPanel.setBorder(new EmptyBorder(10,10,10,10));

            infoPanel.add(new JLabel("Hall Type: " + booking.getHallType()));
            infoPanel.add(new JLabel("Hall Number: " + booking.getHallNumber()));
            infoPanel.add(new JLabel("Date: " + booking.getBookingDate()));
            infoPanel.add(new JLabel("Time: " + booking.getBookingFrom()
                    + " - " + booking.getBookingUntil()));
            infoPanel.add(new JLabel("Amount: RM " + booking.getAmount()));
            infoPanel.add(new JLabel("Status: " + booking.getBookingStatus()));

            // 🔥 BUTTON PANEL
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(Color.WHITE);

            JButton btnCancel = new JButton("Cancel");
            JButton btnIssue = new JButton("Raise Issue");

            // Disable cancel if already cancelled
            if ((booking.getBookingStatus() == Booking.BookingStatus.CANCELLED) || (booking.getBookingStatus() == Booking.BookingStatus.COMPLETED)) {
                btnCancel.setEnabled(false);
            }

            btnCancel.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to cancel this booking?",
                        "Confirm Cancel",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    boolean msg = bookingController.cancelBooking(booking.getBookingId());
                    JOptionPane.showMessageDialog(this, msg);
                    // refresh dashboard
                    if (parentFrame instanceof MainFrame) {
                        ((MainFrame) parentFrame).showDashboard();
                    }
                }
            });

            btnIssue.addActionListener(e -> {
                new IssueDialog((JFrame) SwingUtilities.getWindowAncestor(this), booking.getBookingId(), issueController, () -> {
                    if (parentFrame instanceof MainFrame) {
                        ((MainFrame) parentFrame).showDashboard();
                    }
                });
            });

            buttonPanel.add(btnCancel);
            buttonPanel.add(btnIssue);

            card.add(infoPanel, BorderLayout.CENTER);
            card.add(buttonPanel, BorderLayout.SOUTH);

            container.add(Box.createVerticalStrut(10));
            container.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);
    }
}
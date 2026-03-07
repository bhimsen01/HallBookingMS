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

        bookingDAO = new BookingDAO();

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        // 🔥 Get current logged-in user ID
        String userId = Session.getCurrentUser().getUserId();

        // 🔥 Get only bookings of this user
        List<Booking> myBookings = bookingDAO.getByUserId(userId);
        myBookings = new ArrayList<>(myBookings); // make mutable
        Collections.reverse(myBookings);

        if (myBookings.isEmpty()) {
            JLabel emptyLabel = new JLabel("No bookings found.", SwingConstants.CENTER);
            Font currentFont = emptyLabel.getFont();
            Font newFont = currentFont.deriveFont(16f);
            emptyLabel.setFont(newFont);
            add(emptyLabel, BorderLayout.CENTER);
            return;
        }

        for (Booking booking : myBookings) {


            JPanel card = new JPanel() {
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(50,50,100));
                    g2.fillRoundRect(0,0,getWidth(),getHeight(),20,20);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };

            card.setLayout(new BorderLayout());
            card.setOpaque(false);
            card.setBorder(new EmptyBorder(15,15,15,15));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 240));

            JPanel infoPanel = new JPanel(new GridLayout(0,1));
            infoPanel.setOpaque(false);

            JLabel hallType = new JLabel("Hall Type: " + booking.getHallType());
            JLabel hallNumber = new JLabel("Hall Number: " + booking.getHallNumber());
            JLabel date = new JLabel("Date: " + booking.getBookingDate());
            JLabel time = new JLabel("Time: " + booking.getBookingFrom()
                    + " - " + booking.getBookingUntil());
            JLabel amount = new JLabel("Amount: RM " + booking.getAmount());
            JLabel status = new JLabel("Status: " + booking.getBookingStatus());

            JLabel[] labels = {hallType, hallNumber, date, time, amount, status};

            for (JLabel lbl : labels) {
                lbl.setForeground(Color.WHITE);
                infoPanel.add(lbl);
                infoPanel.add(Box.createVerticalStrut(6));
            }

            // 🔥 BUTTON PANEL
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);

            JButton btnCancel = new JButton("Cancel");
            btnCancel.setBackground(new Color(255, 66, 69));
            JButton btnIssue = new JButton("Raise Issue");

            // Disable cancel if already canceled
            if ((booking.getBookingStatus() == Booking.BookingStatus.CANCELLED) || (booking.getBookingStatus() == Booking.BookingStatus.COMPLETED)) {
                btnCancel.setEnabled(false);
            }

            btnCancel.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to cancel this booking?",
                        "Confirm",
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
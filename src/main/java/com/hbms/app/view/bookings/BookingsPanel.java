package com.hbms.app.view.bookings;

import com.hbms.app.controller.BookingController;
import com.hbms.app.controller.IssueController;
import com.hbms.app.dao.BookingDAO;
import com.hbms.app.model.Booking;
import com.hbms.app.model.User;
import com.hbms.app.session.Session;
import com.hbms.app.view.initial.Refreshable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingsPanel extends JPanel implements Refreshable {

    private final BookingDAO bookingDAO;
    private final JFrame parentFrame;
    private final BookingController bookingController;
    private final IssueController issueController;

    private JPanel container;

    public BookingsPanel(JFrame parentFrame, BookingController bookingController, IssueController issueController) {

        this.parentFrame = parentFrame;
        this.bookingController = bookingController;
        this.issueController = issueController;

        setLayout(new BorderLayout());

        bookingDAO = new BookingDAO();

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(0,5,5,5));

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);

        loadBookings();
    }

    public void loadBookings() {

        container.removeAll();

        String userId = Session.getCurrentUser().getUserId();
        User.Role role = Session.getCurrentUser().getRole();

        List<Booking> myBookings;
        if (role == User.Role.ADMINISTRATOR || role == User.Role.MANAGER) {
            myBookings = bookingDAO.getAllBookings();
        } else {
            myBookings = bookingDAO.getByUserId(userId);
        }
        myBookings = new ArrayList<>(myBookings);
        Collections.reverse(myBookings);

        if (myBookings.isEmpty()) {

            JLabel emptyLabel = new JLabel("No bookings found.", SwingConstants.CENTER);
            Font currentFont = emptyLabel.getFont();
            Font newFont = currentFont.deriveFont(16f);
            emptyLabel.setFont(newFont);

            container.add(emptyLabel);

            container.revalidate();
            container.repaint();
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
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE,280));

            JPanel infoPanel = new JPanel(new GridLayout(0,1));
            infoPanel.setOpaque(false);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            JLabel hallType = new JLabel("Hall Type • " + booking.getHallType());
            JLabel hallNumber = new JLabel("Hall Number • " + booking.getHallNumber());
            JLabel date = new JLabel("Date • " + booking.getBookingDate().format(dateFormatter));
            JLabel time = new JLabel("Time • " + booking.getBookingFrom().format(timeFormatter) + " - " + booking.getBookingUntil().format(timeFormatter));
            JLabel amount = new JLabel("Amount • RM " + booking.getAmount());
            JLabel status = new JLabel("Status • " + booking.getBookingStatus());
            JLabel bookedAt=new JLabel("Booked At • "+booking.getBookingCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));

            JLabel[] labels = {hallType, hallNumber, date, time, amount, status, bookedAt};

            for (JLabel lbl : labels) {
                lbl.setForeground(Color.WHITE);
                infoPanel.add(lbl);
                infoPanel.add(Box.createVerticalStrut(6));
            }

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);

            JButton btnCancel = new JButton("Cancel");
            btnCancel.setBackground(new Color(255,66,69));

            JButton btnIssue = new JButton("Raise Issue");

            if (booking.getBookingStatus() == Booking.BookingStatus.CANCELLED ||
                    booking.getBookingStatus() == Booking.BookingStatus.COMPLETED) {

                btnCancel.setEnabled(false);
            }

            // disable the cancel if it is not before3 days
            long daysUntil = ChronoUnit.DAYS.between(LocalDate.now(), booking.getBookingDate());
            if (daysUntil < 3) {
                btnCancel.setEnabled(false);
                btnCancel.setToolTipText("Cannot cancel within 3 days of booking date.");
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

                    loadBookings();
                }
            });

            btnIssue.addActionListener(e ->

                    new RaiseIssueDialog(
                            (JFrame) SwingUtilities.getWindowAncestor(this),
                            booking.getBookingId(),
                            issueController,
                            this::loadBookings
                    )
            );

            buttonPanel.add(btnCancel);
            buttonPanel.add(btnIssue);

            card.add(infoPanel, BorderLayout.CENTER);
            card.add(buttonPanel, BorderLayout.SOUTH);

            container.add(Box.createVerticalStrut(10));
            container.add(card);
        }

        container.revalidate();
        container.repaint();
    }
    @Override
    public void refresh() {
        loadBookings();
    }
}
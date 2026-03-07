package com.hbms.app.view.analytic;

import com.hbms.app.dao.BookingDAO;
import com.hbms.app.dao.HallDAO;
import com.hbms.app.model.Booking;
import com.hbms.app.model.Hall;
import com.hbms.app.service.SymphonyAccountService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsPanel extends JPanel {

    public AnalyticsPanel() {
        setLayout(new BorderLayout());

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        // Account Card at top
        JPanel accountCard = createAccountCard();
        container.add(accountCard);
        container.add(Box.createVerticalStrut(10));

        // Analytics cards
        JPanel analyticsPanel = new JPanel();
        analyticsPanel.setLayout(new GridLayout(2, 2, 15, 15));
        analyticsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        analyticsPanel.add(createAnalyticsBox("Total Bookings", getTotalBookings()));
        analyticsPanel.add(createAnalyticsBox("This Month Sales", getMonthlyRevenue()));
        analyticsPanel.add(createAnalyticsBox("Total Revenue", getTotalRevenue()));
        analyticsPanel.add(createAnalyticsBox("Available Halls", getAvailableHalls()));

        container.add(analyticsPanel);
        container.add(Box.createVerticalStrut(10));

        // Additional stats
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(1, 3, 15, 15));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        statsPanel.add(createAnalyticsBox("Confirmed Bookings", getConfirmedBookings()));
        statsPanel.add(createAnalyticsBox("Cancelled Bookings", getCancelledBookings()));
        statsPanel.add(createAnalyticsBox("Total Halls", getTotalHalls()));

        container.add(statsPanel);

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createAccountCard() {
        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(50, 50, 100));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        card.setLayout(new BorderLayout());
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        SymphonyAccountService accountService = new SymphonyAccountService();

        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.setOpaque(false);

        JLabel accountNameLabel = new JLabel("Account: " + accountService.getSymphonyAccNme());
        Font currentFont = accountNameLabel.getFont();
        Font newFont = currentFont.deriveFont(18f);
        accountNameLabel.setFont(newFont);
        accountNameLabel.setForeground(Color.WHITE);

        JLabel balanceLabel = new JLabel("Balance: RM " + String.format("%.2f", accountService.getBalance()));
        balanceLabel.setForeground(new Color(200, 200, 255));

        infoPanel.add(accountNameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(balanceLabel);

        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createAnalyticsBox(String title, String value) {
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(60, 70, 130));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(new Color(200, 200, 255));

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        Font currentFont = valueLabel.getFont();
        Font newFont = currentFont.deriveFont(24f);
        valueLabel.setFont(newFont);
        valueLabel.setForeground(Color.WHITE);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    private String getTotalBookings() {
        BookingDAO bookingDAO = new BookingDAO();
        return String.valueOf(bookingDAO.getAllBookings().size());
    }

    private String getConfirmedBookings() {
        BookingDAO bookingDAO = new BookingDAO();
        long count = bookingDAO.getAllBookings().stream()
                .filter(b -> b.getBookingStatus() == Booking.BookingStatus.CONFIRMED)
                .count();
        return String.valueOf(count);
    }

    private String getCancelledBookings() {
        BookingDAO bookingDAO = new BookingDAO();
        long count = bookingDAO.getAllBookings().stream()
                .filter(b -> b.getBookingStatus() == Booking.BookingStatus.CANCELLED)
                .count();
        return String.valueOf(count);
    }

    private String getMonthlyRevenue() {
        BookingDAO bookingDAO = new BookingDAO();
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);

        double revenue = bookingDAO.getAllBookings().stream()
                .filter(b -> YearMonth.from(b.getBookingDate()).equals(currentMonth))
                .filter(b -> b.getBookingStatus() == Booking.BookingStatus.CONFIRMED)
                .mapToDouble(Booking::getAmount)
                .sum();

        return "RM " + String.format("%.2f", revenue);
    }

    private String getTotalRevenue() {
        BookingDAO bookingDAO = new BookingDAO();
        double revenue = bookingDAO.getAllBookings().stream()
                .filter(b -> b.getBookingStatus() == Booking.BookingStatus.CONFIRMED)
                .mapToDouble(Booking::getAmount)
                .sum();

        return "RM " + String.format("%.2f", revenue);
    }

    private String getAvailableHalls() {
        HallDAO hallDAO = new HallDAO();
        long count = hallDAO.getAllHalls().stream()
                .filter(h -> h.getHallStatus() == Hall.HallStatus.AVAILABLE)
                .count();
        return String.valueOf(count);
    }

    private String getTotalHalls() {
        HallDAO hallDAO = new HallDAO();
        return String.valueOf(hallDAO.getAllHalls().size());
    }
}

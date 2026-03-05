package com.hbms.app.view.hall;

import com.hbms.app.controller.BookingController;
import com.hbms.app.controller.HallController;
import com.hbms.app.dao.HallDAO;
import com.hbms.app.model.Hall;
import com.hbms.app.view.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class HallsPanel extends JPanel {

    private final BookingController bookingController;
    private final HallController hallController;
    private final JFrame parentFrame;

    private HallDAO hallDAO;
    private JPanel container;

    public HallsPanel(JFrame parentFrame, BookingController bookingController, HallController hallController) {
        this.parentFrame = parentFrame;
        this.bookingController = bookingController;
        this.hallController = hallController;

        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        hallDAO = new HallDAO();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(new Color(240,240,240));

        JButton btnAddHall = new JButton("Add Hall");
        btnAddHall.addActionListener(e ->
                new AddHallDialog(parentFrame, hallController, this)
        );

        topPanel.add(btnAddHall);
        add(topPanel, BorderLayout.NORTH);

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);

        loadHalls();
    }

    public void loadHalls() {

        container.removeAll();

        List<Hall> halls = hallDAO.getAllHalls();
        Collections.reverse(halls);

        for (Hall hall : halls) {

            JPanel card = new JPanel(new BorderLayout());
            card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            card.setBackground(Color.WHITE);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

            JPanel infoPanel = new JPanel(new GridLayout(0,1));
            infoPanel.setBackground(Color.WHITE);
            infoPanel.setBorder(new EmptyBorder(10,10,10,10));

            infoPanel.add(new JLabel("Type: " + hall.getHallType()));
            infoPanel.add(new JLabel("Hall: " + hall.getHallNumber()));
            infoPanel.add(new JLabel("Capacity: " + hall.getHallCapacity()));
            infoPanel.add(new JLabel("Price: RM " + hall.getHallPrice()));
            infoPanel.add(new JLabel("Status: " + hall.getHallStatus()));

            JButton bookButton = new JButton("Book Now");
            JButton editButton = new JButton("Edit");
            JButton deleteButton = new JButton("Delete");

            if (hall.getHallStatus() != Hall.HallStatus.AVAILABLE) {
                bookButton.setEnabled(false);
            }

            bookButton.addActionListener(e ->
                    new HallBookingDialog(parentFrame, hall, bookingController, () -> {
                        SwingUtilities.invokeLater(() -> {
                            if (parentFrame instanceof MainFrame) {
                                ((MainFrame) parentFrame).showDashboard();
                            } else {
                                loadHalls();
                            }
                        });
                    })
            );

            editButton.addActionListener(e -> {
                new EditHallDialog(parentFrame, hallController, this, hall);
            });

            deleteButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(this, "Delete this hall?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean msg = hallController.deleteHall(hall.getHallNumber());
                    JOptionPane.showMessageDialog(this, msg);
                    loadHalls();
                }
            });

            card.add(infoPanel, BorderLayout.CENTER);
            JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            south.setBackground(Color.WHITE);
            south.add(bookButton);
            south.add(editButton);
            south.add(deleteButton);
            card.add(south, BorderLayout.SOUTH);

            container.add(Box.createVerticalStrut(10));
            container.add(card);
        }

        container.revalidate();
        container.repaint();
    }
}
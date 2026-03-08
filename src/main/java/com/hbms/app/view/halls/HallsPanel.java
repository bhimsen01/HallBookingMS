package com.hbms.app.view.halls;

import com.hbms.app.controller.BookingController;
import com.hbms.app.controller.HallController;
import com.hbms.app.controller.ReceiptController;
import com.hbms.app.dao.HallDAO;
import com.hbms.app.model.Hall;
import com.hbms.app.model.User;
import com.hbms.app.view.initial.Refreshable;
import com.hbms.app.session.Session;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class HallsPanel extends JPanel implements Refreshable {

    private final BookingController bookingController;
    private final HallController hallController;
    private final JFrame parentFrame;
    private final ReceiptController receiptController;

    private HallDAO hallDAO;
    private JPanel container;

    public HallsPanel(JFrame parentFrame, BookingController bookingController, HallController hallController, ReceiptController receiptController) {
        this.parentFrame = parentFrame;
        this.bookingController = bookingController;
        this.hallController = hallController;
        this.receiptController = receiptController;

        setLayout(new BorderLayout());

        hallDAO = new HallDAO();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnAddHall = new JButton("Add Hall");
        btnAddHall.addActionListener(e ->
                new AddHallDialog(parentFrame, hallController, this)
        );

        if (Session.getCurrentUser().getRole() == User.Role.CUSTOMER) {
            btnAddHall.setVisible(false);
        }

        topPanel.add(btnAddHall);
        add(topPanel, BorderLayout.NORTH);

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);

        loadHalls();
    }

    public void loadHalls() {

        container.removeAll();

        List<Hall> halls = hallDAO.getAllHalls();
        Collections.reverse(halls);

        if (halls.isEmpty()) {
            JLabel emptyLabel = new JLabel("No hall exists.", SwingConstants.CENTER);
            Font currentFont = emptyLabel.getFont();
            Font newFont = currentFont.deriveFont(16f);
            emptyLabel.setFont(newFont);
            add(emptyLabel, BorderLayout.CENTER);
            return;
        }

        for (Hall hall : halls) {

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
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));

            JPanel infoPanel = new JPanel(new GridLayout(0,1));
            infoPanel.setOpaque(false);

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            JLabel type = new JLabel("Type • " + hall.getHallType());
            JLabel number = new JLabel("Hall • " + hall.getHallNumber());
            JLabel capacity = new JLabel("Capacity • " + hall.getHallCapacity());
            JLabel price = new JLabel("Price • RM " + hall.getHallPrice());
            JLabel status = new JLabel("Status • " + hall.getHallStatus());
            JLabel availableFrom = new JLabel("Available From • " + hall.getHallAvailableFrom().format(timeFormatter));
            JLabel availableUntil = new JLabel("Available Until • " + hall.getHallAvailableUntil().format(timeFormatter));
            JLabel remarks=new JLabel("Remarks • "+hall.getHallRemarks());

            JLabel[] labels = {type, number, capacity, price, status, availableFrom, availableUntil, remarks};

            for (JLabel lbl : labels) {
                lbl.setForeground(Color.WHITE);
                infoPanel.add(lbl);
                infoPanel.add(Box.createVerticalStrut(6));
            }

            JButton bookButton = new JButton("Book Hall");
            JButton editButton = new JButton("Edit");
            JButton deleteButton = new JButton("Delete");
            deleteButton.setBackground(new Color(255, 66, 69));

            if (hall.getHallStatus() != Hall.HallStatus.AVAILABLE) {
                bookButton.setEnabled(false);
            }

            bookButton.addActionListener(e ->
                    new BookHallDialog(
                            parentFrame,
                            hall,
                            bookingController,
                            receiptController
                    )
            );

            editButton.addActionListener(e -> {
                new EditHallDialog(parentFrame, hallController, this, hall);
            });

            deleteButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(this, "Delete Hall "+hall.getHallNumber() +"?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    hallController.deleteHall(hall.getHallNumber());
                    JOptionPane.showMessageDialog(this, "Hall deleted.");
                    loadHalls();
                }
            });

            JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            south.setOpaque(false);

            if (Session.getCurrentUser().getRole() == User.Role.CUSTOMER) {
                editButton.setVisible(false);
                deleteButton.setVisible(false);
            }

            south.add(bookButton);
            south.add(editButton);
            south.add(deleteButton);

            card.add(infoPanel, BorderLayout.CENTER);
            card.add(south, BorderLayout.SOUTH);

            container.add(Box.createVerticalStrut(10));
            container.add(card);
        }

        container.revalidate();
        container.repaint();
    }

    @Override
    public void refresh() {
        loadHalls();
    }
}
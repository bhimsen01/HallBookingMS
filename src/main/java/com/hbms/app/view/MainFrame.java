package com.hbms.app.view;

import com.hbms.app.controller.*;
import com.hbms.app.dao.*;
import com.hbms.app.service.*;
import com.hbms.app.utility.IdCounter;
import com.hbms.app.view.initial.DashboardPanel;
import com.hbms.app.view.initial.LoginPanel;
import com.hbms.app.view.initial.SignupPanel;
import com.hbms.app.view.initial.StartPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private BookingController bookingController;
    private HallController hallController;
    private IssueController issueController;
    private UserController userController;

    public MainFrame() {
        IdCounter idCounter = new IdCounter();

        // DAOs
        BookingDAO bookingDAO = new BookingDAO();
        ReceiptDAO receiptDAO = new ReceiptDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        HallDAO hallDAO = new HallDAO();
        IssueDAO issueDAO = new IssueDAO();
        UserDAO userDAO = new UserDAO();

        // Services
        BookingService bookingService = new BookingService(idCounter, bookingDAO);
        ReceiptService receiptService = new ReceiptService(idCounter, receiptDAO);
        PaymentService paymentService = new PaymentService(idCounter, paymentDAO);
        HallService hallService = new HallService(hallDAO);
        IssueService issueService = new IssueService(idCounter, issueDAO);
        UserService userService = new UserService(idCounter, userDAO);

        // Controllers - initialize all here
        this.bookingController = new BookingController(bookingService, new ReceiptController(receiptService), new PaymentController(paymentService));
        this.hallController = new HallController(hallService);
        this.issueController = new IssueController(issueService);
        this.userController = new UserController(userService);

        // JFrame setup
        setTitle("Hall Symphony");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add panels
        mainPanel.add(new StartPanel(this), "START");
        mainPanel.add(new LoginPanel(this, userController), "LOGIN");
        mainPanel.add(new SignupPanel(this, userController), "SIGNUP");

        add(mainPanel);

        setVisible(true);
    }

    public void showScreen(String name) {
        cardLayout.show(mainPanel, name);
    }

    public void showDashboard() {
        // Pass all initialized controllers here
        DashboardPanel dashboard = new DashboardPanel(this, bookingController, hallController, issueController, userController);

        mainPanel.add(dashboard, "DASHBOARD");
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
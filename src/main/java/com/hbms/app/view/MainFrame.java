package com.hbms.app.view;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
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
import java.awt.event.MouseWheelEvent;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private BookingController bookingController;
    private HallController hallController;
    private IssueController issueController;
    private UserController userController;
    private ReceiptController receiptController;
    private UserDAO userDAO;
    private BookingService bookingService;

    public MainFrame() {
        IdCounter idCounter = new IdCounter();

        // DAOs
        BookingDAO bookingDAO = new BookingDAO();
        ReceiptDAO receiptDAO = new ReceiptDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        HallDAO hallDAO = new HallDAO();
        IssueDAO issueDAO = new IssueDAO();
        this.userDAO=new UserDAO();

        // Services
        this.bookingService = new BookingService(idCounter, bookingDAO);
        ReceiptService receiptService = new ReceiptService(idCounter, receiptDAO);
        PaymentService paymentService = new PaymentService(idCounter, paymentDAO);
        HallService hallService = new HallService(hallDAO);
        IssueService issueService = new IssueService(idCounter, issueDAO);
        UserService userService = new UserService(idCounter, userDAO);

        // Controllers - initialize all here
        this.receiptController = new ReceiptController(receiptService);
        this.bookingController = new BookingController(bookingService, receiptController, new PaymentController(paymentService));
        this.hallController = new HallController(hallService);
        this.issueController = new IssueController(issueService);
        this.userController = new UserController(userService);

        // JFrame setup
        setTitle("Hall Symphony");
        setSize(1280, 800);
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
        DashboardPanel dashboard = new DashboardPanel(this, bookingController, hallController, issueController, userController, receiptController, userDAO, bookingService);

        mainPanel.add(dashboard, "DASHBOARD");
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    private static void setGlobalScrollSpeed(int pixelsPerStep) {
        UIManager.put("ScrollBar.unitIncrement", pixelsPerStep);

        // Optional: for dynamically added scroll panes
        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if (event instanceof MouseWheelEvent e) {
                Component comp = e.getComponent();
                while (comp != null && !(comp instanceof JScrollPane)) {
                    comp = comp.getParent();
                }
                if (comp instanceof JScrollPane sp) {
                    sp.getVerticalScrollBar().setUnitIncrement(pixelsPerStep);
                    sp.getHorizontalScrollBar().setUnitIncrement(pixelsPerStep);
                }
            }
        }, AWTEvent.MOUSE_WHEEL_EVENT_MASK);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());

            // Rounded UI
            UIManager.put("Component.arc", 18);
            UIManager.put("Button.arc", 20);
            UIManager.put("TextComponent.arc", 12);
            UIManager.put("ProgressBar.arc", 12);
            UIManager.put("TitlePane.unifiedBackground", true);

            // Larger spacing
            UIManager.put("Button.margin", new Insets(8,16,8,16));
            UIManager.put("Component.focusWidth", 1);

            // Accent color (Windows 11 style blue)
            Color accent = new Color(0,145,255);
            UIManager.put("Component.focusColor", accent);
            UIManager.put("Button.default.background", accent);

            // Load Inter variable font from resources and force it
            Font interVariable = Font.createFont(
                    Font.TRUETYPE_FONT,
                    MainFrame.class.getResourceAsStream("/fonts/Inter-VariableFont_opsz,wght.ttf")
            ).deriveFont(Font.BOLD, 14f);

            UIManager.put("defaultFont", interVariable);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Launch the main frame
        SwingUtilities.invokeLater(() -> {
            setGlobalScrollSpeed(20);
            MainFrame frame = new MainFrame();
            // Force all nested components to use Inter immediately
            SwingUtilities.updateComponentTreeUI(frame);
        });
    }
}
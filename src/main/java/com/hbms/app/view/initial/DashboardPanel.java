package com.hbms.app.view.initial;

import com.hbms.app.controller.BookingController;
import com.hbms.app.controller.HallController;
import com.hbms.app.controller.IssueController;
import com.hbms.app.controller.UserController;
import com.hbms.app.dao.UserDAO;
import com.hbms.app.service.BookingService;
import com.hbms.app.view.MainFrame;
import com.hbms.app.view.analytics.AnalyticsPanel;
import com.hbms.app.view.boards.BoardPanel;
import com.hbms.app.view.bookings.BookingsPanel;
import com.hbms.app.view.halls.HallsPanel;
import com.hbms.app.view.home.HomePanel;
import com.hbms.app.view.issues.IssuesPanel;
import com.hbms.app.view.profile.ProfilePanel;
import com.hbms.app.view.settings.SettingsPanel;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private final MainFrame mainFrame;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private final BookingController bookingController;
    private final HallController hallController;
    private final IssueController issueController;
    private final UserController userController;
    private final com.hbms.app.controller.ReceiptController receiptController;
    private HallsPanel hallsPanel;
    private BookingsPanel bookingsPanel;
    private IssuesPanel issuesPanel;
    private final UserDAO userDAO;
    private final BookingService bookingService;

    public DashboardPanel(MainFrame mainFrame, BookingController bookingController, HallController hallController, IssueController issueController, UserController userController, com.hbms.app.controller.ReceiptController receiptController, UserDAO userDAO, BookingService bookingService){
        this.mainFrame=mainFrame;
        this.bookingController=bookingController;
        this.hallController=hallController;
        this.issueController = issueController;
        this.userController = userController;
        this.receiptController = receiptController;
        this.userDAO = userDAO;
        this.bookingService = bookingService;
        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        contentPanel=new JPanel(cardLayout);

        contentPanel.add(new HomePanel(), "HOME");
        hallsPanel = new HallsPanel(mainFrame, bookingController, hallController, receiptController);
        contentPanel.add(hallsPanel, "HALLS");

        bookingsPanel = new BookingsPanel(mainFrame, bookingController, issueController);
        contentPanel.add(bookingsPanel, "BOOKINGS");

        issuesPanel = new IssuesPanel(mainFrame, issueController);
        contentPanel.add(issuesPanel, "ISSUES");
        contentPanel.add(new AnalyticsPanel(bookingService), "ANALYTICS");
        contentPanel.add(new BoardPanel(userController), "BOARD");
        contentPanel.add(new ProfilePanel(userController, userDAO), "PROFILE");
        contentPanel.add(new SettingsPanel(), "SETTINGS");

        add(new SidebarPanel(this), BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    public void showContent(String name) {
        // refresh all refreshable panels before showing
        for(Component c : contentPanel.getComponents()){
            if(c instanceof Refreshable){
                ((Refreshable)c).refresh();
            }
        }

        // show the requested panel
        cardLayout.show(contentPanel, name);
    }
}

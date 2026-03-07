package com.hbms.app.view.initial;

import com.hbms.app.controller.BookingController;
import com.hbms.app.controller.HallController;
import com.hbms.app.controller.IssueController;
import com.hbms.app.controller.UserController;
import com.hbms.app.view.MainFrame;
import com.hbms.app.view.analytic.AnalyticsPanel;
import com.hbms.app.view.board.BoardPanel;
import com.hbms.app.view.booking.BookingsPanel;
import com.hbms.app.view.hall.HallsPanel;
import com.hbms.app.view.home.HomePanel;
import com.hbms.app.view.issue.IssuesPanel;
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

    public DashboardPanel(MainFrame mainFrame, BookingController bookingController, HallController hallController, IssueController issueController, UserController userController, com.hbms.app.controller.ReceiptController receiptController){
        this.mainFrame=mainFrame;
        this.bookingController=bookingController;
        this.hallController=hallController;
        this.issueController = issueController;
        this.userController = userController;
        this.receiptController = receiptController;
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
        contentPanel.add(new AnalyticsPanel(), "ANALYTICS");
        contentPanel.add(new BoardPanel(userController), "BOARD");
        contentPanel.add(new ProfilePanel(userController), "PROFILE");
        contentPanel.add(new SettingsPanel(), "SETTINGS");

        add(new SidebarPanel(this), BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    public void showContent(String name) {
        cardLayout.show(contentPanel, name);
    }
}

package com.hbms.app.view;

import com.hbms.app.controller.UserController;
import com.hbms.app.dao.UserDAO;
import com.hbms.app.service.UserService;
import com.hbms.app.utility.IdCounter;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame(){
        setTitle("Hall Symphony");
        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout=new CardLayout();
        mainPanel=new JPanel(cardLayout);

        IdCounter idCounter=new IdCounter();
        UserDAO userDAO=new UserDAO();
        UserService userService=new UserService(idCounter, userDAO);
        UserController userController=new UserController(userService);

        mainPanel.add(new StartPanel(this),"START");
        mainPanel.add(new LoginPanel(this, userController),"LOGIN");
        mainPanel.add(new SignupPanel(this, userController),"SIGNUP");
//        mainPanel.add(new HomePanel(this),"HOME");
//        mainPanel.add(new DashboardPanel(this),"DASHBOARD");

        add(mainPanel);
        setVisible(true);
    }

    public void showScreen(String name){
        cardLayout.show(mainPanel,name);
    }

    static void main(String[] args) {
        new MainFrame();
    }
}

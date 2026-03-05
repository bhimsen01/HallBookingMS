//package com.hbms.app.test;
//
//import com.hbms.app.auth.AuthUser;
//import com.hbms.app.controller.IssueController;
//import com.hbms.app.dao.IssueDAO;
//import com.hbms.app.model.Issue;
//import com.hbms.app.model.User;
//import com.hbms.app.service.IssueService;
//import com.hbms.app.session.Session;
//import com.hbms.app.utility.IdCounter;
//
//public class IssueTest {
//    static void main(String[] args) {
//        IdCounter idCounter=new IdCounter();
//        IssueDAO issueDAO=new IssueDAO();
//        IssueService issueService=new IssueService(idCounter, issueDAO);
//        IssueController issueController=new IssueController(issueService);
//
//        AuthUser testUser = new AuthUser("U100", "bhimsen", "basnet", "bhimsen@123", User.Role.CUSTOMER);
//        Session.login(testUser);
//
//        Issue issue=new Issue();
//        issue.setBookingId("B102");
//        issue.setDescription("Very big problem");
//        issueController.raiseIssue(issue);
//
//        System.out.println("This is test of issues.");
//
//        Session.logout();
//    }
//}

package com.hbms.app.controller;

import com.hbms.app.model.Issue;
import com.hbms.app.service.IssueService;

public class IssueController {
    private final IssueService issueService;

    public IssueController(IssueService issueService){
        this.issueService=issueService;
    }

    public void raiseIssue(Issue issue){
        try{
            issueService.raiseIssue(issue);
            System.out.println("Issue raised successfully.");
        } catch (Exception e) {
            System.out.println("Failed to raise issue. "+e.getMessage());
        }
    }
}

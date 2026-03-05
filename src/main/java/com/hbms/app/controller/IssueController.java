package com.hbms.app.controller;

import com.hbms.app.model.Issue;
import com.hbms.app.service.IssueService;

public class IssueController {
    private final IssueService issueService;

    public IssueController(IssueService issueService){
        this.issueService=issueService;
    }

    public boolean raiseIssue(Issue issue){
        try{
            issueService.raiseIssue(issue);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editIssue(String issueId, Issue updatedIssue){
        try{
            issueService.editIssue(issueId, updatedIssue);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

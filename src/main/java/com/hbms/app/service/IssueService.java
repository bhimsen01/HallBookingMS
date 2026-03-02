package com.hbms.app.service;

import com.hbms.app.auth.AuthUser;
import com.hbms.app.dao.IssueDAO;
import com.hbms.app.model.Issue;
import com.hbms.app.session.Session;
import com.hbms.app.utility.IdCounter;

import java.time.LocalDateTime;

public class IssueService {
    private final IdCounter idCounter;
    private final IssueDAO issueDAO;

    public IssueService(IdCounter idCounter, IssueDAO issueDAO){
        this.idCounter=idCounter;
        this.issueDAO=issueDAO;
    }

    public void raiseIssue(Issue issue){
        AuthUser currentUser= Session.getCurrentUser();
        issue.setIssueId(idCounter.generateId("ISSUE","I"));
        issue.setRaisedBy(currentUser.getUserId());
        issue.setIssueCreatedAt(LocalDateTime.now());
        issue.setIssueStatus(Issue.IssueStatus.OPEN);

        issueDAO.saveIssue(issue);
    }
}

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
        try{
            issueDAO.saveIssue(issue);
        } catch (Exception e) {
            throw new RuntimeException("Failed to raise issue. ",e);
        }
    }

    public void editIssue(String issueId, Issue editedIssue){
        Issue issueToUpdate=issueDAO.findById(issueId);

        if (issueToUpdate==null){
            throw new RuntimeException("Issue to update is not found.");
        }

        if (editedIssue.getDescription() != null && !editedIssue.getDescription().isEmpty())
            issueToUpdate.setDescription(editedIssue.getDescription());

        if (editedIssue.getIssueStatus() != null)
            issueToUpdate.setIssueStatus(editedIssue.getIssueStatus());

        if (editedIssue.getAssignedStaffId() != null && !editedIssue.getAssignedStaffId().isEmpty())
            issueToUpdate.setAssignedStaffId(editedIssue.getAssignedStaffId());

        if (editedIssue.getIssueManagerRemarks() != null && !editedIssue.getIssueManagerRemarks().isEmpty())
            issueToUpdate.setIssueManagerRemarks(editedIssue.getIssueManagerRemarks());

        if (editedIssue.getIssueStatus() == Issue.IssueStatus.RESOLVED && editedIssue.getIssueResolvedAt() != null)
            issueToUpdate.setIssueResolvedAt(editedIssue.getIssueResolvedAt());

        try{
            issueDAO.editIssue(issueToUpdate);
            System.out.println("Issue edited successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to edit issue. ",e);
        }
    }
}

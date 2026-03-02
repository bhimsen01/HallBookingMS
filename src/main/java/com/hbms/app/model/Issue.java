package com.hbms.app.model;

import java.time.LocalDateTime;

public class Issue {
    public enum IssueStatus{
        OPEN,
        IN_PROGRESS,
        RESOLVED,
        CANCELLED
    }
    private String issueId;
    private String bookingId;
    private String raisedBy;
    private String description;
    private String assignedStaffId;
    private LocalDateTime issueCreatedAt;
    private LocalDateTime issueResolvedAt;
    private String issueManagerRemarks;
    private IssueStatus issueStatus;

    public Issue() {
    }

    public Issue(String issueId, String bookingId, String raisedBy, String description, String assignedStaffId, LocalDateTime issueCreatedAt, LocalDateTime issueResolvedAt, String issueManagerRemarks, IssueStatus issueStatus) {
        this.issueId = issueId;
        this.bookingId = bookingId;
        this.raisedBy = raisedBy;
        this.description = description;
        this.assignedStaffId = assignedStaffId;
        this.issueCreatedAt = issueCreatedAt;
        this.issueResolvedAt = issueResolvedAt;
        this.issueManagerRemarks = issueManagerRemarks;
        this.issueStatus = issueStatus;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getRaisedBy() {
        return raisedBy;
    }

    public void setRaisedBy(String raisedBy) {
        this.raisedBy = raisedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssignedStaffId() {
        return assignedStaffId;
    }

    public void setAssignedStaffId(String assignedStaffId) {
        this.assignedStaffId = assignedStaffId;
    }

    public LocalDateTime getIssueCreatedAt() {
        return issueCreatedAt;
    }

    public void setIssueCreatedAt(LocalDateTime issueCreatedAt) {
        this.issueCreatedAt = issueCreatedAt;
    }

    public LocalDateTime getIssueResolvedAt() {
        return issueResolvedAt;
    }

    public void setIssueResolvedAt(LocalDateTime issueResolvedAt) {
        this.issueResolvedAt = issueResolvedAt;
    }

    public String getIssueManagerRemarks() {
        return issueManagerRemarks;
    }

    public void setIssueManagerRemarks(String issueManagerRemarks) {
        this.issueManagerRemarks = issueManagerRemarks;
    }

    public IssueStatus getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
    }
}

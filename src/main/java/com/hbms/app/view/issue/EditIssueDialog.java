package com.hbms.app.view.issue;

import com.hbms.app.controller.IssueController;
import com.hbms.app.model.Issue;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class EditIssueDialog extends JDialog {

    private final IssueController issueController;
    private final IssuesPanel issuesPanel;
    private final Issue issue;

    public EditIssueDialog(JFrame parentFrame, IssueController issueController, IssuesPanel issuesPanel, Issue issue) {
        super(parentFrame, "Edit Issue #" + issue.getIssueId(), true);

        this.issueController = issueController;
        this.issuesPanel = issuesPanel;
        this.issue = issue;

        setSize(450, 350);
        setLocationRelativeTo(parentFrame);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Issue ID (read-only)
        JTextField tfIssueId = new JTextField(issue.getIssueId());
        tfIssueId.setEditable(false);

        // Booking ID (read-only)
        JTextField tfBookingId = new JTextField(issue.getBookingId());
        tfBookingId.setEditable(false);

        // Description
        JTextArea taDescription = new JTextArea(issue.getDescription());
        taDescription.setLineWrap(true);
        taDescription.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(taDescription);

        // Status dropdown
        JComboBox<Issue.IssueStatus> cbStatus = new JComboBox<>(Issue.IssueStatus.values());
        cbStatus.setSelectedItem(issue.getIssueStatus());

        // Assigned Staff ID
        JTextField tfAssignedStaffId = new JTextField(issue.getAssignedStaffId() != null ? issue.getAssignedStaffId() : "");

        // Manager Remarks
        JTextArea taRemarks = new JTextArea(issue.getIssueManagerRemarks() != null ? issue.getIssueManagerRemarks() : "");
        taRemarks.setLineWrap(true);
        taRemarks.setWrapStyleWord(true);
        JScrollPane scrollRemarks = new JScrollPane(taRemarks);

        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        add(new JLabel("Issue ID:"));
        add(tfIssueId);

        add(new JLabel("Booking ID:"));
        add(tfBookingId);

        add(new JLabel("Description:"));
        add(scrollDesc);

        add(new JLabel("Status:"));
        add(cbStatus);

        add(new JLabel("Assigned Staff ID:"));
        add(tfAssignedStaffId);

        add(new JLabel("Manager Remarks:"));
        add(scrollRemarks);

        add(btnSave);
        add(btnCancel);

        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> {
            try {
                String description = taDescription.getText().trim();
                Issue.IssueStatus status = (Issue.IssueStatus) cbStatus.getSelectedItem();
                String assignedStaffId = tfAssignedStaffId.getText().trim();
                String remarks = taRemarks.getText().trim();

                if (description.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Description cannot be empty.");
                    return;
                }

                Issue updatedIssue = new Issue();
                updatedIssue.setDescription(description);
                updatedIssue.setIssueStatus(status);
                updatedIssue.setAssignedStaffId(assignedStaffId.isEmpty() ? null : assignedStaffId);
                updatedIssue.setIssueManagerRemarks(remarks.isEmpty() ? null : remarks);

                // If marking as RESOLVED, set resolved time
                if (status == Issue.IssueStatus.RESOLVED) {
                    updatedIssue.setIssueResolvedAt(LocalDateTime.now());
                }

                boolean msg = issueController.editIssue(issue.getIssueId(), updatedIssue);
                JOptionPane.showMessageDialog(this, msg);
                issuesPanel.loadIssues();
                dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating issue: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}

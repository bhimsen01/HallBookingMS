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

        super(parentFrame, "Edit Issue • " + issue.getIssueId(), true);

        this.issueController = issueController;
        this.issuesPanel = issuesPanel;
        this.issue = issue;

        setSize(500, 450);
        setLocationRelativeTo(parentFrame);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(20,30,20,30)
        );

        // ===== Fields =====

        JTextField tfIssueId = new JTextField(issue.getIssueId());
        tfIssueId.setEditable(false);

        JTextField tfBookingId = new JTextField(issue.getBookingId());
        tfBookingId.setEditable(false);

        JTextArea taDescription = new JTextArea(issue.getDescription());
        taDescription.setLineWrap(true);
        taDescription.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(taDescription);

        JComboBox<Issue.IssueStatus> cbStatus =
                new JComboBox<>(Issue.IssueStatus.values());
        cbStatus.setSelectedItem(issue.getIssueStatus());

        JTextField tfAssignedStaffId =
                new JTextField(issue.getAssignedStaffId() != null ?
                        issue.getAssignedStaffId() : "");

        JTextArea taRemarks = new JTextArea(
                issue.getIssueManagerRemarks() != null ?
                        issue.getIssueManagerRemarks() : ""
        );
        taRemarks.setLineWrap(true);
        taRemarks.setWrapStyleWord(true);
        JScrollPane scrollRemarks = new JScrollPane(taRemarks);

        JButton btnSave = new JButton("Save");
        btnSave.setBackground(new Color(0,145,255));
        btnSave.setForeground(Color.WHITE);

        JButton btnCancel = new JButton("Cancel");

        // ===== Layout =====

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        add(new JLabel("Issue ID"), gbc);

        gbc.gridx = 1;
        add(tfIssueId, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        add(new JLabel("Booking ID"), gbc);

        gbc.gridx = 1;
        add(tfBookingId, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        add(new JLabel("Description"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        add(scrollDesc, gbc);

        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        add(new JLabel("Status"), gbc);

        gbc.gridx = 1;
        add(cbStatus, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        add(new JLabel("Assigned Staff ID"), gbc);

        gbc.gridx = 1;
        add(tfAssignedStaffId, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        add(new JLabel("Manager Remarks"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        add(scrollRemarks, gbc);

        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== Button Panel =====

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        add(buttonPanel, gbc);

        // ===== Actions =====

        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> {

            try {

                String description = taDescription.getText().trim();
                Issue.IssueStatus status =
                        (Issue.IssueStatus) cbStatus.getSelectedItem();
                String assignedStaffId =
                        tfAssignedStaffId.getText().trim();
                String remarks = taRemarks.getText().trim();

                if(description.isEmpty()){
                    JOptionPane.showMessageDialog(this,
                            "Description cannot be empty.");
                    return;
                }

                Issue updatedIssue = new Issue();
                updatedIssue.setDescription(description);
                updatedIssue.setIssueStatus(status);
                updatedIssue.setAssignedStaffId(
                        assignedStaffId.isEmpty() ? null : assignedStaffId
                );
                updatedIssue.setIssueManagerRemarks(
                        remarks.isEmpty() ? null : remarks
                );

                if(status == Issue.IssueStatus.RESOLVED){
                    updatedIssue.setIssueResolvedAt(LocalDateTime.now());
                }

                boolean success = issueController.editIssue(
                        issue.getIssueId(), updatedIssue
                );

                if(success){
                    JOptionPane.showMessageDialog(this,
                            "Issue updated successfully!");
                    issuesPanel.loadIssues();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Failed to update issue.");
                }

            } catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error updating issue: " + ex.getMessage());
            }

        });

        setVisible(true);
    }
}
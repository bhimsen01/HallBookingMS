package com.hbms.app.view.issues;

import com.hbms.app.controller.IssueController;
import com.hbms.app.model.Issue;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class UpdateIssueDialog extends JDialog {
    private final IssueController issueController;
    private final IssuesPanel issuesPanel;
    private final Issue issue;
    private JLabel lblMessage;

    public UpdateIssueDialog(JFrame parentFrame, IssueController issueController, IssuesPanel issuesPanel, Issue issue) {

        super(parentFrame, "Update Issue", true);

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

        // fields

        JTextField tfIssueId = new JTextField(issue.getIssueId());
        tfIssueId.setFocusable(false);
        tfIssueId.setEditable(false);


        JTextField tfBookingId = new JTextField(issue.getBookingId());
        tfBookingId.setFocusable(false);
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

        // layout

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

        lblMessage = new JLabel("");
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblMessage);
        add(new JLabel(""));

        add(buttonPanel, gbc);


        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> {
            try {
                String description = taDescription.getText().trim();
                String assignedStaffId = tfAssignedStaffId.getText().trim();
                String remarks = taRemarks.getText().trim();
                Issue.IssueStatus status = (Issue.IssueStatus) cbStatus.getSelectedItem();

                if(description.isEmpty() && assignedStaffId.isEmpty() && remarks.isEmpty()){
                    lblMessage.setForeground(new Color(255, 66, 69));
                    lblMessage.setText("Empty fields.");
                    return;
                }


//        if(userDAO.findById(assignedStaffId) == null){
//            lblMessage.setForeground(new Color(255, 66,69));
//            lblMessage.setText("Assigned staff does not exist.");
//            return;
//        }

                Issue updatedIssue = new Issue();
                updatedIssue.setDescription(description);
                updatedIssue.setIssueStatus(status);
                updatedIssue.setAssignedStaffId(assignedStaffId.isEmpty() ? null : assignedStaffId);
                updatedIssue.setIssueManagerRemarks(remarks.isEmpty() ? null : remarks);

                if(status == Issue.IssueStatus.RESOLVED){
                    updatedIssue.setIssueResolvedAt(LocalDateTime.now());
                }

                boolean success = issueController.editIssue(issue.getIssueId(), updatedIssue);

                if(success){
                    JOptionPane.showMessageDialog(this, "Issue updated successfully.");
                    issuesPanel.loadIssues();
                    dispose();
                } else {
                    lblMessage.setForeground(new Color(255, 66,69));
                    lblMessage.setText("Failed to update issue.");
                }

            } catch(Exception ex){
                ex.printStackTrace();
                lblMessage.setForeground(new Color(255,66,69));
                lblMessage.setText("Error updating issue.");
            }
        });

        setVisible(true);
    }
}
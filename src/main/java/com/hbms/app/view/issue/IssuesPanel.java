package com.hbms.app.view.issue;

import com.hbms.app.dao.IssueDAO;
import com.hbms.app.model.Issue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class IssuesPanel extends JPanel {
    private IssueDAO issueDAO;
    private JPanel container;
    private com.hbms.app.controller.IssueController issueController;

    public IssuesPanel(com.hbms.app.controller.IssueController issueController) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        this.issueController = issueController;
        issueDAO = new IssueDAO();

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        loadIssues();
    }

    public void loadIssues() {
        container.removeAll();
        List<Issue> issues = issueDAO.getAllIssues();

        if (issues.isEmpty()) {
            JLabel emptyLabel = new JLabel("No issues found.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.BOLD, 16));
            container.add(emptyLabel);
            container.revalidate();
            container.repaint();
            return;
        }

        for (Issue issue : issues) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            card.setBackground(Color.WHITE);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

            JPanel infoPanel = new JPanel(new GridLayout(0, 1));
            infoPanel.setBackground(Color.WHITE);
            infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

            infoPanel.add(new JLabel("Issue ID: " + issue.getIssueId()));
            infoPanel.add(new JLabel("Booking ID: " + issue.getBookingId()));
            infoPanel.add(new JLabel("Description: " + issue.getDescription()));
            infoPanel.add(new JLabel("Status: " + issue.getIssueStatus()));

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(Color.WHITE);

            JButton btnEdit = new JButton("Edit");
            JButton btnCancel = new JButton("Cancel Issue");

            if (issue.getIssueStatus() == Issue.IssueStatus.CANCELLED || issue.getIssueStatus() == Issue.IssueStatus.RESOLVED) {
                btnCancel.setEnabled(false);
            }

            btnEdit.addActionListener(e -> {
                new EditIssueDialog((JFrame) SwingUtilities.getWindowAncestor(this), issueController, this, issue);
            });

            btnCancel.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(this, "Cancel this issue?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Issue updatedIssue = new Issue();
                    updatedIssue.setIssueStatus(Issue.IssueStatus.CANCELLED);
                    issueController.editIssue(issue.getIssueId(), updatedIssue);
                    loadIssues();
                    JOptionPane.showMessageDialog(this, "Issue cancelled.");
                }
            });

            buttonPanel.add(btnEdit);
            buttonPanel.add(btnCancel);

            card.add(infoPanel, BorderLayout.CENTER);
            card.add(buttonPanel, BorderLayout.SOUTH);

            container.add(Box.createVerticalStrut(10));
            container.add(card);
        }

        container.revalidate();
        container.repaint();
    }
}

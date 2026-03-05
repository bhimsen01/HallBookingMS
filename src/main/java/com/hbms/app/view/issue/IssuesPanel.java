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
        this.issueController = issueController;
        issueDAO = new IssueDAO();

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

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
            JPanel card = new JPanel() {
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(50,50,100));
                    g2.fillRoundRect(0,0,getWidth(),getHeight(),20,20);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };

            card.setLayout(new BorderLayout());
            card.setOpaque(false);
            card.setBorder(new EmptyBorder(15,15,15,15));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

            JPanel infoPanel = new JPanel(new GridLayout(0, 1));
            infoPanel.setOpaque(false);

            Font labelFont = new Font("Inter", Font.PLAIN, 14);

            JLabel issueId = new JLabel("Issue ID: " + issue.getIssueId());
            JLabel bookingId = new JLabel("Booking ID: " + issue.getBookingId());
            JLabel description = new JLabel("Description: " + issue.getDescription());
            JLabel status = new JLabel("Status: " + issue.getIssueStatus());

            JLabel[] labels = {issueId, bookingId, description, status};

            for (JLabel lbl : labels) {
                lbl.setForeground(Color.WHITE);
                lbl.setFont(labelFont);
            }

            infoPanel.add(issueId);
            infoPanel.add(bookingId);
            infoPanel.add(description);
            infoPanel.add(status);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);

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

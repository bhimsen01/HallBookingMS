package com.hbms.app.view.issues;

import com.hbms.app.controller.IssueController;
import com.hbms.app.dao.IssueDAO;
import com.hbms.app.model.Issue;
import com.hbms.app.session.Session;
import com.hbms.app.view.initial.Refreshable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class IssuesPanel extends JPanel implements Refreshable {
    private IssueDAO issueDAO;
    private IssueController issueController;
    private JPanel container;

    public IssuesPanel(JFrame parentFrame, IssueController issueController) {
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
        String userId = Session.getCurrentUser().getUserId();
        List<Issue> myIssues = issueDAO.getByUserId(userId);

        if (myIssues.isEmpty()) {
            JLabel emptyLabel = new JLabel("No issues found.", SwingConstants.CENTER);
            Font currentFont = emptyLabel.getFont();
            Font newFont = currentFont.deriveFont(16f);
            emptyLabel.setFont(newFont);
            container.add(emptyLabel);
            container.revalidate();
            container.repaint();
            return;
        }

        for (Issue issue : myIssues) {
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
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 340));

            JPanel infoPanel = new JPanel(new GridLayout(0, 1));
            infoPanel.setOpaque(false);


            JLabel issueId = new JLabel("Issue ID • " + issue.getIssueId());
            JLabel bookingId = new JLabel("Booking ID • " + issue.getBookingId());
            JLabel description = new JLabel("Description • " + issue.getDescription());
            JLabel status = new JLabel("Status • " + issue.getIssueStatus());
            JLabel raisedBy=new JLabel("Raised By • " + issue.getRaisedBy());
            JLabel assignedStaffId=new JLabel("Assigned Staff • "+issue.getAssignedStaffId());
            JLabel issueManagerRemarks=new JLabel("Manager Remarks • "+issue.getIssueManagerRemarks());
            JLabel createdAt=new JLabel("Created At • "+issue.getIssueCreatedAt());
            JLabel resolvedAt=new JLabel("Resolved At • "+issue.getIssueResolvedAt());

            JLabel[] labels = {issueId, bookingId, description, status, raisedBy, assignedStaffId, issueManagerRemarks, createdAt, resolvedAt};

            for (JLabel lbl : labels) {
                lbl.setForeground(Color.WHITE);
                infoPanel.add(lbl);
                infoPanel.add(Box.createVerticalStrut(6));
            }

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);

            JButton btnUpdate = new JButton("Update");
            JButton btnEdit = new JButton("Edit");
            JButton btnCancel = new JButton("Cancel Issue");
            btnCancel.setBackground(new Color(255, 66, 69));

            if (issue.getIssueStatus() == Issue.IssueStatus.CANCELLED || issue.getIssueStatus() == Issue.IssueStatus.RESOLVED) {
                btnCancel.setEnabled(false);
            }

            btnUpdate.addActionListener(e -> {
                new UpdateIssueDialog((JFrame) SwingUtilities.getWindowAncestor(this), issueController, this, issue);
            });

            btnEdit.addActionListener(e -> {
                new EditIssueDialog(
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        issueController,
                        this::loadIssues,
                        issue
                );
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

            buttonPanel.add(btnUpdate);
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

    @Override
    public void refresh() {
        loadIssues();
    }
}

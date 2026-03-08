package com.hbms.app.view.issues;

import com.hbms.app.controller.IssueController;
import com.hbms.app.model.Issue;

import javax.swing.*;
import java.awt.*;

public class EditIssueDialog extends JDialog {
    private JLabel lblMessage;

    public EditIssueDialog(JFrame parentFrame, IssueController issueController, Runnable onSuccess, Issue issue) {
        super(parentFrame, "Edit Issue", true);

        setSize(500, 400);
        setLocationRelativeTo(parentFrame);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // ===== Booking ID (read-only) =====
        JPanel bookingPanel = new JPanel(new BorderLayout(10, 10));
        JLabel lblBooking = new JLabel("Booking ID");
        JTextField tfBookingId = new JTextField(issue.getBookingId());
        tfBookingId.setEditable(false);
        tfBookingId.setFocusable(false);
        tfBookingId.setPreferredSize(new Dimension(200, 30));
        bookingPanel.add(lblBooking, BorderLayout.WEST);
        bookingPanel.add(tfBookingId, BorderLayout.CENTER);

        // ===== Description =====
        JPanel descPanel = new JPanel(new BorderLayout(10, 10));
        JLabel lblDesc = new JLabel("Description");

        JTextArea taDesc = new JTextArea(issue.getDescription());
        taDesc.setLineWrap(true);
        taDesc.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(taDesc);
        scroll.setPreferredSize(new Dimension(200, 120));

        descPanel.add(lblDesc, BorderLayout.NORTH);
        descPanel.add(scroll, BorderLayout.CENTER);

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnSave = new JButton("Save");
        btnSave.setBackground(new Color(0, 145, 255));
        btnSave.setForeground(Color.WHITE);
        btnSave.setPreferredSize(new Dimension(120, 35));

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(new Dimension(120, 35));

        lblMessage = new JLabel("");
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // ===== Add panels =====
        mainPanel.add(bookingPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(descPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(lblMessage);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);

        add(mainPanel);

        // ===== Actions =====
        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> {
            String description = taDesc.getText().trim();
            if (description.isEmpty()) {
                lblMessage.setForeground(new Color(255, 66, 69));
                lblMessage.setText("Description cannot be empty.");
                return;
            }

            issue.setDescription(description);
            boolean success = issueController.editIssue(issue.getIssueId(), issue);

            if (success) {
                JOptionPane.showMessageDialog(this, "Issue edited successfully.");
                if (onSuccess != null) onSuccess.run();
                dispose();
            } else {
                lblMessage.setForeground(new Color(255, 66, 69));
                lblMessage.setText("Failed to edit issue.");
            }
        });

        setVisible(true);
    }
}
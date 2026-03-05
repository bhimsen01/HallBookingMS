package com.hbms.app.view.issue;

import com.hbms.app.controller.IssueController;
import com.hbms.app.model.Issue;

import javax.swing.*;
import java.awt.*;

public class IssueDialog extends JDialog {

    public IssueDialog(JFrame parentFrame, String bookingId, IssueController issueController, Runnable onSuccess) {
        super(parentFrame, "Raise Issue", true);
        setSize(400, 250);
        setLocationRelativeTo(parentFrame);
        setLayout(new GridLayout(4,1,8,8));

        JTextField tfBookingId = new JTextField(bookingId);
        tfBookingId.setEditable(false);
        JTextArea taDesc = new JTextArea();
        JScrollPane scroll = new JScrollPane(taDesc);

        JButton btnSubmit = new JButton("Submit");
        JButton btnCancel = new JButton("Cancel");

        add(new JLabel("Booking ID:"));
        add(tfBookingId);
        add(new JLabel("Description:"));
        add(scroll);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnSubmit);
        btnPanel.add(btnCancel);
        add(btnPanel);

        btnCancel.addActionListener(e -> dispose());

        btnSubmit.addActionListener(e -> {
            String desc = taDesc.getText().trim();
            if (desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter issue description.");
                return;
            }

            Issue issue = new Issue();
            issue.setBookingId(bookingId);
            issue.setDescription(desc);

            issueController.raiseIssue(issue);
            JOptionPane.showMessageDialog(this, "Issue submitted.");
            if (onSuccess!=null) {
                try { onSuccess.run(); } catch (Exception ex) { ex.printStackTrace(); }
            }
            dispose();
        });

        setVisible(true);
    }
}

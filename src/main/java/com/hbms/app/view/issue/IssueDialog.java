package com.hbms.app.view.issue;

import com.hbms.app.controller.IssueController;
import com.hbms.app.model.Issue;

import javax.swing.*;
import java.awt.*;

public class IssueDialog extends JDialog {

    public IssueDialog(JFrame parentFrame, String bookingId, IssueController issueController, Runnable onSuccess) {
        super(parentFrame, "Raise Issue", true);
        setSize(500, 400);
        setLocationRelativeTo(parentFrame);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));

        Font labelFont = new Font("Inter", Font.PLAIN, 14);

        JTextField tfBookingId = new JTextField(bookingId);
        tfBookingId.setEditable(false);
        tfBookingId.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JTextArea taDesc = new JTextArea();
        taDesc.setLineWrap(true);
        taDesc.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(taDesc);
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        JButton btnSubmit = new JButton("Submit");
        JButton btnCancel = new JButton("Cancel");

        btnSubmit.setPreferredSize(new Dimension(120,40));
        btnCancel.setPreferredSize(new Dimension(120,40));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnCancel);

        // ===== Build Form =====
        addField(formPanel, "Booking ID", tfBookingId, labelFont);
        addField(formPanel, "Description", scroll, labelFont);

        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(buttonPanel);

        add(formPanel);

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

    private void addField(JPanel panel, String label, Component field, Font font) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(font);
        lbl.setHorizontalAlignment(SwingConstants.LEFT);
        lbl.setMaximumSize(new Dimension(Integer.MAX_VALUE, lbl.getPreferredSize().height));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(4));
        panel.add(field);
        panel.add(Box.createVerticalStrut(12));
    }
}

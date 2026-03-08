package com.hbms.app.view.bookings;

import com.hbms.app.controller.IssueController;
import com.hbms.app.model.Issue;

import javax.swing.*;
import java.awt.*;

public class RaiseIssueDialog extends JDialog {
    private JLabel lblMessage;

    public RaiseIssueDialog(JFrame parentFrame, String bookingId, IssueController issueController, Runnable onSuccess) {

        super(parentFrame, "Raise Issue", true);

        setSize(500, 450);
        setLocationRelativeTo(parentFrame);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));

        // ===== Booking ID =====
        JPanel bookingPanel = new JPanel(new BorderLayout(10,10));
        JLabel lblBooking = new JLabel("Booking ID");
        JTextField tfBookingId = new JTextField(bookingId);
        tfBookingId.setEditable(false);
        tfBookingId.setFocusable(false);
        tfBookingId.setPreferredSize(new Dimension(200,30));

        bookingPanel.add(lblBooking, BorderLayout.WEST);
        bookingPanel.add(tfBookingId, BorderLayout.CENTER);

        // ===== Description =====
        JPanel descPanel = new JPanel(new BorderLayout(10,10));
        JLabel lblDesc = new JLabel("Description");

        JTextArea taDesc = new JTextArea();
        taDesc.setLineWrap(true);
        taDesc.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(taDesc);
        scroll.setPreferredSize(new Dimension(200,120)); // bigger textarea

        descPanel.add(lblDesc, BorderLayout.NORTH);
        descPanel.add(scroll, BorderLayout.CENTER);

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBackground(new Color(0,145,255));
        btnSubmit.setPreferredSize(new Dimension(120,35));

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(new Dimension(120,35));

        lblMessage = new JLabel("");
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblMessage);
        add(new JLabel(""));

        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnCancel);

        // ===== Add to main =====
        mainPanel.add(bookingPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(descPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);

        add(mainPanel);

        // ===== Actions =====
        btnCancel.addActionListener(e -> dispose());

        btnSubmit.addActionListener(e -> {

            String desc = taDesc.getText().trim();

            if(desc.isEmpty()){
                lblMessage.setForeground(new Color(255,66,69));
                lblMessage.setText("Empty issue description.");
                return;
            }

            Issue issue = new Issue();
            issue.setBookingId(bookingId);
            issue.setDescription(desc);

            issueController.raiseIssue(issue);

            JOptionPane.showMessageDialog(this,"Issue submitted.");

            if(onSuccess != null){
                try{
                    onSuccess.run();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            dispose();
        });

        setVisible(true);
    }
}
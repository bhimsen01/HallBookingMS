package com.hbms.app.model;

import java.time.LocalDateTime;

public class Payment {
    public enum PaymentType{
        INCOMING,
        OUTGOING
    }
//    public enum PaymentGateway{
//        BHIMSENCHASE,
//        BHIMSENEXPRESS
//    }
    private String paymentId;
    private String bookingId;
    private double amount;
    private PaymentType paymentType;
//    private PaymentGateway paymentGateway;
    private LocalDateTime paymentCreatedAt;

    public Payment() {
    }

    public Payment(String paymentId, String bookingId, double amount, PaymentType paymentType, LocalDateTime paymentCreatedAt) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.paymentCreatedAt=paymentCreatedAt;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDateTime getPaymentCreatedAt() {
        return paymentCreatedAt;
    }

    public void setPaymentCreatedAt(LocalDateTime paymentCreatedAt) {
        this.paymentCreatedAt = paymentCreatedAt;
    }
}

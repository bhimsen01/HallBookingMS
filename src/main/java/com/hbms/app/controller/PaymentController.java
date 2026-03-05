package com.hbms.app.controller;

import com.hbms.app.model.Payment;
import com.hbms.app.service.PaymentService;

public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService=paymentService;
    }

    public boolean createPayment(String bookingId, double amount, Payment.PaymentType paymentType){
        try{
            Payment payment=new Payment();
            payment.setBookingId(bookingId);
            payment.setAmount(amount);
            payment.setPaymentType(paymentType);
            paymentService.createPayment(payment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

package com.hbms.app.service;

import com.hbms.app.dao.PaymentDAO;
import com.hbms.app.model.Payment;
import com.hbms.app.utility.IdCounter;

import java.time.LocalDateTime;

public class PaymentService {
    private final IdCounter idCounter;
    private final PaymentDAO paymentDAO;

    public PaymentService(IdCounter idCounter, PaymentDAO paymentDAO){
        this.idCounter=idCounter;
        this.paymentDAO=paymentDAO;
    }

    public void createPayment(Payment payment){
        payment.setPaymentId(idCounter.generateId("PAYMENT","P"));
        payment.setPaymentCreatedAt(LocalDateTime.now());
        paymentDAO.savePayment(payment);
        System.out.println("payment service");
    }
}

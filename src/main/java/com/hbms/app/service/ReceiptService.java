package com.hbms.app.service;

import com.hbms.app.dao.ReceiptDAO;
import com.hbms.app.model.Receipt;
import com.hbms.app.utility.IdCounter;

import java.time.LocalDateTime;

public class ReceiptService {
    private final IdCounter idCounter;
    private final ReceiptDAO receiptDAO;

    public ReceiptService(IdCounter idCounter, ReceiptDAO receiptDAO){
        this.idCounter=idCounter;
        this.receiptDAO=receiptDAO;
    }

    public void generateReceipt(String bookingId){
        Receipt receipt=new Receipt();
        receipt.setReceptID(idCounter.generateId("RECEIPT","R"));
        receipt.setBookingId(bookingId);
        receipt.setReceiptCreatedAt(LocalDateTime.now());
        receiptDAO.saveReceipt(receipt);
    }
}

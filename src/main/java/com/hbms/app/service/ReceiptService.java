package com.hbms.app.service;

import com.hbms.app.dao.ReceiptDAO;
import com.hbms.app.model.Receipt;
import com.hbms.app.utility.IdCounter;

import java.time.LocalDateTime;
import java.util.List;

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
        try{
            receiptDAO.saveReceipt(receipt);
            System.out.println("Receipt generated successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate receipt. ",e);
        }
    }

    public Receipt getReceiptByBookingId(String bookingId){
        List<Receipt> receipts = receiptDAO.getAllReceipts();
        for(Receipt receipt : receipts){
            if(receipt.getBookingId().equals(bookingId)){
                return receipt;
            }
        }
        return null;
    }
}

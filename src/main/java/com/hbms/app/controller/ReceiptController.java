package com.hbms.app.controller;

import com.hbms.app.service.ReceiptService;

public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService){
        this.receiptService=receiptService;
    }

    public boolean generateReceipt(String bookingId){
        try{
            receiptService.generateReceipt(bookingId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

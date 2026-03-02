package com.hbms.app.controller;

import com.hbms.app.service.ReceiptService;

public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService){
        this.receiptService=receiptService;
    }

    public String generateReceipt(String bookingId){
        try{
            receiptService.generateReceipt(bookingId);
            System.out.println("Receipt generated successfully.");
            return "Receipt generated successfully.";
        } catch (Exception e) {
            System.out.println("Receipt generation failed. "+e.getMessage());
            return "Receipt generation failed. ";
        }
    }
}

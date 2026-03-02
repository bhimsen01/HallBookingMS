package com.hbms.app.model;

import java.time.LocalDateTime;

public class Receipt {
    private String receptID;
    private String bookingId;
    private LocalDateTime receiptCreatedAt;

    public Receipt() {
    }

    public Receipt(String receptID, String bookingId, LocalDateTime receiptCreatedAt) {
        this.receptID = receptID;
        this.bookingId = bookingId;
        this.receiptCreatedAt = receiptCreatedAt;
    }

    public String getReceptID() {
        return receptID;
    }

    public void setReceptID(String receptID) {
        this.receptID = receptID;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDateTime getReceiptCreatedAt() {
        return receiptCreatedAt;
    }

    public void setReceiptCreatedAt(LocalDateTime receiptCreatedAt) {
        this.receiptCreatedAt = receiptCreatedAt;
    }
}

package com.hbms.app.controller;

import com.hbms.app.model.Booking;
import com.hbms.app.model.Payment;
import com.hbms.app.service.BookingService;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingController {
    private final BookingService bookingService;
    private final ReceiptController receiptController;
    private final PaymentController paymentController;

    public BookingController(BookingService bookingService, ReceiptController receiptController, PaymentController paymentController){
        this.bookingService=bookingService;
        this.receiptController = receiptController;
        this.paymentController = paymentController;
    }

    public String addBooking(String hallType, String hallNumber, String bookingDate, String bookingFrom, String bookingUntil, double amount){
        try{
           Booking booking=new Booking(hallType,Integer.parseInt(hallNumber), LocalDate.parse(bookingDate), LocalTime.parse(bookingFrom), LocalTime.parse(bookingUntil), amount);
           bookingService.bookHall(booking);
           paymentController.createPayment(booking.getBookingId(), booking.getAmount(), Payment.PaymentType.INCOMING);
            System.out.println("After payment controller");
           receiptController.generateReceipt(booking.getBookingId());
           return "Hall booked successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Booking failed. "+e.getMessage();
        }
    }

}

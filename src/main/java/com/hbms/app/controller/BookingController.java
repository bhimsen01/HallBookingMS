package com.hbms.app.controller;

import com.hbms.app.model.Booking;
import com.hbms.app.model.Payment;
import com.hbms.app.service.BookingService;
import com.hbms.app.service.SymphonyAccountService;

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

    public boolean addBooking(String hallType, String hallNumber, String bookingDate, String bookingFrom, String bookingUntil, double amount){
        try{
           Booking booking=new Booking(hallType,Integer.parseInt(hallNumber), LocalDate.parse(bookingDate), LocalTime.parse(bookingFrom), LocalTime.parse(bookingUntil), amount);
           bookingService.bookHall(booking);
           new SymphonyAccountService().addBookingAmount(amount);
           paymentController.createPayment(booking.getBookingId(), booking.getAmount(), Payment.PaymentType.INCOMING);
           receiptController.generateReceipt(booking.getBookingId());
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelBooking(String bookingId){
        try{
            bookingService.cancelBooking(bookingId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

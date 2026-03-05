package com.hbms.app.service;

import com.hbms.app.auth.AuthUser;
import com.hbms.app.dao.BookingDAO;
import com.hbms.app.model.Booking;
import com.hbms.app.model.User;
import com.hbms.app.session.Session;
import com.hbms.app.utility.IdCounter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class BookingService {
    private final IdCounter idCounter;
    private final BookingDAO bookingDAO;

    public BookingService(IdCounter idCounter, BookingDAO bookingDAO){
        this.idCounter=idCounter;
        this.bookingDAO=bookingDAO;
    }

    public void bookHall(Booking booking){
        AuthUser currentUser= Session.getCurrentUser();
        booking.setBookingId(idCounter.generateId("BOOKING","B"));
        booking.setUserId(currentUser.getUserId());
        booking.setBookingCreatedAt(LocalDateTime.now());
        booking.setBookingStatus(Booking.BookingStatus.CONFIRMED);

        try{
            bookingDAO.saveBooking(booking);
            System.out.println("Hall booked successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to book hall.",e);
        }
    }

    public void cancelBooking(String bookingId){
        Booking bookingToCancel=bookingDAO.findById(bookingId);

        if(bookingToCancel==null){
            throw new RuntimeException("Booking to cancel is not found.");
        }

        bookingToCancel.setBookingStatus(Booking.BookingStatus.CANCELLED);

        try{
            bookingDAO.updateBooking(bookingToCancel);
            System.out.println("Booking is cancelled.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to cancel booking. ",e);
        }
    }

//    public void completeBooking(String bookingId){
//        Booking bookingToCancel=bookingDAO.findById(bookingId);
//
//        bookingToCancel.setBookingStatus(Booking.BookingStatus.CANCELLED);
//
//        try{
//            bookingDAO.updateBooking(bookingToCancel);
//            System.out.println("Booking is completed.");
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to complete booking. ",e);
//        }
//    }
}

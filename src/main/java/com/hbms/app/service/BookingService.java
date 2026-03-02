package com.hbms.app.service;

import com.hbms.app.auth.AuthUser;
import com.hbms.app.dao.BookingDAO;
import com.hbms.app.model.Booking;
import com.hbms.app.session.Session;
import com.hbms.app.utility.IdCounter;

import java.time.LocalDateTime;

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

        bookingDAO.saveBooking(booking);
    }
}

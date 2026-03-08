package com.hbms.app.service;

import com.hbms.app.auth.AuthUser;
import com.hbms.app.dao.BookingDAO;
import com.hbms.app.dao.HallDAO;
import com.hbms.app.model.Booking;
import com.hbms.app.model.Hall;
import com.hbms.app.session.Session;
import com.hbms.app.utility.IdCounter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

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

        // check if hall booked for the selected time
        if (!bookingDAO.isHallAvailable(booking.getHallNumber(), booking.getBookingDate(), booking.getBookingFrom(), booking.getBookingUntil())) {
            throw new RuntimeException("Hall is not available at the selected time slot.");
        }

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

    public long getTotalBookings() {
        BookingDAO bookingDAO = new BookingDAO();
        return (bookingDAO.getAllBookings().size());
    }

    public long getConfirmedBookings() {
        BookingDAO bookingDAO = new BookingDAO();
        long count = bookingDAO.getAllBookings().stream()
                .filter(b -> b.getBookingStatus() == Booking.BookingStatus.CONFIRMED)
                .count();
        return count;
    }

    public long getCancelledBookings() {
        BookingDAO bookingDAO = new BookingDAO();
        long count = bookingDAO.getAllBookings().stream()
                .filter(b -> b.getBookingStatus() == Booking.BookingStatus.CANCELLED)
                .count();
        return count;
    }

    public String getMonthlyRevenue() {
        BookingDAO bookingDAO = new BookingDAO();
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);

        double revenue = bookingDAO.getAllBookings().stream()
                .filter(b -> YearMonth.from(b.getBookingDate()).equals(currentMonth))
                .filter(b -> b.getBookingStatus() == Booking.BookingStatus.CONFIRMED)
                .mapToDouble(Booking::getAmount)
                .sum();

        return "RM " + String.format("%.2f", revenue);
    }

    public String getTotalRevenue() {
        BookingDAO bookingDAO = new BookingDAO();
        double revenue = bookingDAO.getAllBookings().stream()
                .filter(b -> b.getBookingStatus() == Booking.BookingStatus.CONFIRMED)
                .mapToDouble(Booking::getAmount)
                .sum();

        return "RM " + String.format("%.2f", revenue);
    }

    public long getAvailableHalls() {
        HallDAO hallDAO = new HallDAO();
        long count = hallDAO.getAllHalls().stream()
                .filter(h -> h.getHallStatus() == Hall.HallStatus.AVAILABLE)
                .count();
        return count;
    }

    public long getTotalHalls() {
        HallDAO hallDAO = new HallDAO();
        return hallDAO.getAllHalls().size();
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

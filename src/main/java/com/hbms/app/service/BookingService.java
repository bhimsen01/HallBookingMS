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

        bookingDAO.saveBooking(booking);
    }

    public void cancelBooking(String bookingId){
        List<Booking> bookings=bookingDAO.getAllBookings();
        boolean found=false;

        for(Booking booking:bookings){
            if (booking.getBookingId().equals(bookingId)){
                booking.setBookingStatus(Booking.BookingStatus.CANCELLED);

                found =true;
                break;
            }
        }

        if(!found){
            System.out.println("Booking to cancel not found.");
            return;
        }

        try(BufferedWriter bw=new BufferedWriter(new FileWriter("data/bookings.txt"))){
            for (Booking booking:bookings){
                String line=booking.getBookingId()+","
                        +booking.getUserId()+","
                        +booking.getHallType()+","
                        +booking.getHallNumber()+","
                        +booking.getBookingDate()+","
                        +booking.getBookingFrom()+","
                        +booking.getBookingUntil()+","
                        +booking.getAmount()+","
                        +booking.getBookingCreatedAt()+","
                        +booking.getBookingStatus();
                bw.write(line);
                bw.newLine();
            }
            System.out.println("Updated bookings.txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void completeBooking(){
        List<Booking> bookings=bookingDAO.getAllBookings();

        for(Booking booking:bookings){
            if (booking.getBookingStatus()== Booking.BookingStatus.CONFIRMED  && (LocalDate.now().isEqual(booking.getBookingDate()) && LocalTime.now().isAfter(booking.getBookingUntil()))){
                booking.setBookingStatus(Booking.BookingStatus.COMPLETED);
                break;
            }
        }

        try(BufferedWriter bw=new BufferedWriter(new FileWriter("data/bookings.txt"))){
            for (Booking booking:bookings){
                String line=booking.getBookingId()+","
                        +booking.getUserId()+","
                        +booking.getHallType()+","
                        +booking.getHallNumber()+","
                        +booking.getBookingDate()+","
                        +booking.getBookingFrom()+","
                        +booking.getBookingUntil()+","
                        +booking.getAmount()+","
                        +booking.getBookingCreatedAt()+","
                        +booking.getBookingStatus();
                bw.write(line);
                bw.newLine();
            }
            System.out.println("Updated bookings.txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

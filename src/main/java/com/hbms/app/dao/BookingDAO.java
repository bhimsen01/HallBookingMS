package com.hbms.app.dao;

import com.hbms.app.model.Booking;
import com.hbms.app.model.Hall;
import com.hbms.app.session.Session;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private final String file="data/bookings.txt";

    private String convertToLine(Booking booking){
        return booking.getBookingId()+","
                +booking.getUserId()+","
                +booking.getHallType()+","
                +booking.getHallNumber()+","
                +booking.getBookingDate()+","
                +booking.getBookingFrom()+","
                +booking.getBookingUntil()+","
                +booking.getAmount()+","
                +booking.getBookingCreatedAt()+","
                +booking.getBookingStatus();
    }

    public void saveBooking(Booking booking){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(file,true))){
            bw.write(convertToLine(booking));
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save booking. ",e);
        }
    }

    public void saveAllBookings(List<Booking> bookings){
        try (BufferedWriter bw=new BufferedWriter(new FileWriter(file))){
            for(Booking booking:bookings){
                bw.write(convertToLine(booking));
                bw.newLine();
            }
        } catch (IOException e){
            throw new RuntimeException("Failed to save all bookings. ",e);
        }
    }

    public List<Booking> getAllBookings(){
        List<Booking> bookings=new ArrayList<>();

        try (BufferedReader br=new BufferedReader(new FileReader(file))){
            String line;
            while ((line=br.readLine())!=null){
                if (line.isEmpty()) {
                    System.out.println("Empty line | DAO | getAllBookings");
                    continue;}
                String[] parts=line.split(",");
                Booking booking=new Booking(parts[0], parts[1], Hall.HallType.valueOf(parts[2]), Integer.parseInt(parts[3]), LocalDate.parse(parts[4]), LocalTime.parse(parts[5]), LocalTime.parse(parts[6]), Double.parseDouble(parts[7]), LocalDateTime.parse(parts[8]), Booking.BookingStatus.valueOf(parts[9]));
                bookings.add(booking);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all bookings, ",e);
        }
        return bookings;
    }

    public List<Booking> getByUserId(String userId){
        return getAllBookings().stream().filter(booking -> booking.getUserId().equalsIgnoreCase(userId)).toList();
    }

    public Booking findById(String bookingId){
        return getAllBookings().stream().filter(booking -> booking.getBookingId().equalsIgnoreCase(bookingId)).findFirst().orElse(null);
    }

    public void updateBooking(Booking updatedBooking){
        List<Booking> bookings = getAllBookings();

        boolean found = false;

        for(int i = 0; i < bookings.size(); i++){
            if(bookings.get(i).getBookingId().equals(updatedBooking.getBookingId())){
                bookings.set(i, updatedBooking);
                found = true;
                break;
            }
        }

        if(!found){
            throw new RuntimeException("Booking to update is not found.");
        }

        saveAllBookings(bookings);
    }

    public boolean isHallAvailable(int hallNumber, LocalDate date, LocalTime from, LocalTime until) {
        return getAllBookings().stream()
                .filter(b -> b.getHallNumber() == hallNumber)
                .filter(b -> b.getBookingDate().equals(date))
                .filter(b -> b.getBookingStatus() == Booking.BookingStatus.CONFIRMED)
                .noneMatch(b -> !(until.isBefore(b.getBookingFrom()) || from.isAfter(b.getBookingUntil())));
    }
}

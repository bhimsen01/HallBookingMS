package com.hbms.app.dao;

import com.hbms.app.model.Booking;
import com.hbms.app.model.Hall;
import com.hbms.app.session.Session;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private final String file="data/bookings.txt";

    public void saveBooking(Booking booking){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(file,true))){
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Booking> getAllBookings(){
        List<Booking> bookings=new ArrayList<>();

        try (BufferedReader br=new BufferedReader(new FileReader(file))){
            String line;
            while ((line=br.readLine())!=null){
                String[] parts=line.split(",");
                Booking booking=new Booking(parts[0], parts[1], Hall.HallType.valueOf(parts[2]), Integer.parseInt(parts[3]), LocalDate.parse(parts[4]), LocalTime.parse(parts[5]), LocalTime.parse(parts[6]), Double.parseDouble(parts[7]), LocalDateTime.parse(parts[8]), Booking.BookingStatus.valueOf(parts[9]));
                bookings.add(booking);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bookings;
    }

    public Booking findById(String id){
        return getAllBookings().stream().filter(booking -> booking.getBookingId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}

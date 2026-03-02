package com.hbms.app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Booking {
    public enum BookingStatus {
        COMPLETED,
        CONFIRMED,
        CANCELLED
    };
    private String bookingId;
    private String userId;
    private Hall.HallType hallType;
    private int hallNumber;
    private LocalDate bookingDate;
    private LocalTime bookingFrom;
    private LocalTime bookingUntil;
    private double amount;
    private LocalDateTime bookingCreatedAt;
    public BookingStatus bookingStatus;

    public Booking() {
    }

    public Booking(String hallType,
                   int hallNumber,
                   LocalDate bookingDate,
                   LocalTime bookingFrom,
                   LocalTime bookingUntil,
                   double amount) {

        this.hallType = Hall.HallType.valueOf(hallType);
        this.hallNumber = hallNumber;
        this.bookingDate = bookingDate;
        this.bookingFrom = bookingFrom;
        this.bookingUntil = bookingUntil;
        this.amount = amount;
    }

    public Booking(String bookingId, String userId, Hall.HallType hallType, int hallNumber, LocalDate bookingDate, LocalTime bookingFrom, LocalTime bookingUntil, double amount, LocalDateTime bookingCreatedAt, BookingStatus bookingStatus) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.hallType = hallType;
        this.hallNumber = hallNumber;
        this.bookingDate = bookingDate;
        this.bookingFrom = bookingFrom;
        this.bookingUntil = bookingUntil;
        this.amount = amount;
        this.bookingCreatedAt = bookingCreatedAt;
        this.bookingStatus = bookingStatus;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Hall.HallType getHallType() {
        return hallType;
    }

    public void setHallType(Hall.HallType hallType) {
        this.hallType = hallType;
    }

    public int getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(int hallNumber) {
        this.hallNumber = hallNumber;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalTime getBookingFrom() {
        return bookingFrom;
    }

    public void setBookingFrom(LocalTime bookingFrom) {
        this.bookingFrom = bookingFrom;
    }

    public LocalTime getBookingUntil() {
        return bookingUntil;
    }

    public void setBookingUntil(LocalTime bookingUntil) {
        this.bookingUntil = bookingUntil;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getBookingCreatedAt() {
        return bookingCreatedAt;
    }

    public void setBookingCreatedAt(LocalDateTime bookingCreatedAt) {
        this.bookingCreatedAt = bookingCreatedAt;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}

package com.hbms.app.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Hall {
    public enum HallType{
        AUDITORIUM,
        BANQUET,
        BOARDROOM
    }
    public enum HallStatus{
        AVAILABLE,
        BOOKED,
        CLOSED
    }
    private int hallNumber;
    private HallType hallType;
    private int hallCapacity;
    private double hallPrice;
    private LocalTime hallAvailableFrom;
    private LocalTime hallAvailableUntil;
    private HallStatus hallStatus;
    private String hallRemarks;
    private LocalDateTime hallAddedAt;

    public Hall() {
    }

    public Hall(int hallNumber, HallType hallType, int hallCapacity, double hallPrice, LocalTime hallAvailableFrom, LocalTime hallAvailableUntil, HallStatus hallStatus, String hallRemarks, LocalDateTime hallAddedAt) {
        this.hallNumber = hallNumber;
        this.hallType = hallType;
        this.hallCapacity = hallCapacity;
        this.hallPrice = hallPrice;
        this.hallAvailableFrom = hallAvailableFrom;
        this.hallAvailableUntil = hallAvailableUntil;
        this.hallStatus = hallStatus;
        this.hallRemarks = hallRemarks;
        this.hallAddedAt=hallAddedAt;
    }

    public int getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(int hallNumber) {
        this.hallNumber = hallNumber;
    }

    public HallType getHallType() {
        return hallType;
    }

    public void setHallType(HallType hallType) {
        this.hallType = hallType;
    }

    public int getHallCapacity() {
        return hallCapacity;
    }

    public void setHallCapacity(int hallCapacity) {
        this.hallCapacity = hallCapacity;
    }

    public double getHallPrice() {
        return hallPrice;
    }

    public void setHallPrice(double hallPrice) {
        this.hallPrice = hallPrice;
    }

    public LocalTime getHallAvailableFrom() {
        return hallAvailableFrom;
    }

    public void setHallAvailableFrom(LocalTime hallAvailableFrom) {
        this.hallAvailableFrom = hallAvailableFrom;
    }

    public LocalTime getHallAvailableUntil() {
        return hallAvailableUntil;
    }

    public void setHallAvailableUntil(LocalTime hallAvailableUntil) {
        this.hallAvailableUntil = hallAvailableUntil;
    }

    public HallStatus getHallStatus() {
        return hallStatus;
    }

    public void setHallStatus(HallStatus hallStatus) {
        this.hallStatus = hallStatus;
    }

    public String getHallRemarks() {
        return hallRemarks;
    }

    public void setHallRemarks(String hallRemarks) {
        this.hallRemarks = hallRemarks;
    }

    public LocalDateTime getHallAddedAt() {
        return hallAddedAt;
    }

    public void setHallAddedAt(LocalDateTime hallAddedAt) {
        this.hallAddedAt = hallAddedAt;
    }
}

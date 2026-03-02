package com.hbms.app.dto;

import com.hbms.app.model.Hall;

import java.time.LocalTime;

public class HallDTO {
    private final Hall.HallType hallType;
    private final Hall.HallStatus hallStatus;
    private final int hallNumber;
    private final String hallCapacity;
    private final double HallPrice;
    private final LocalTime hallAvailableFrom;
    private final LocalTime hallAvailableUntil;
    private final String hallRemarks;


    public HallDTO(Hall.HallType hallType, Hall.HallStatus hallStatus, int hallNumber, String hallCapacity, double hallPrice, LocalTime hallAvailableFrom, LocalTime hallAvailableUntil, String hallRemarks) {
        this.hallType = hallType;
        this.hallStatus = hallStatus;
        this.hallNumber = hallNumber;
        this.hallCapacity = hallCapacity;
        this.HallPrice = hallPrice;
        this.hallAvailableFrom=hallAvailableFrom;
        this.hallAvailableUntil=hallAvailableUntil;
        this.hallRemarks = hallRemarks;
    }

    public Hall.HallType getHallType() {
        return hallType;
    }

    public Hall.HallStatus getHallStatus() {
        return hallStatus;
    }

    public int getHallNumber() {
        return hallNumber;
    }

    public String getHallCapacity() {
        return hallCapacity;
    }

    public double getHallPrice() {
        return HallPrice;
    }

    public LocalTime getHallAvailableFrom() {
        return hallAvailableFrom;
    }

    public LocalTime getHallAvailableUntil() {
        return hallAvailableUntil;
    }

    private String getHallRemarks(){
        return hallRemarks;
    };
}

package com.hbms.app.service;

import com.hbms.app.dao.HallDAO;
import com.hbms.app.model.Hall;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HallService {
    private final HallDAO hallDAO;

    public HallService(HallDAO hallDAO){
        this.hallDAO=hallDAO;
    }

    public void addHall(Hall hall){
        if(hallDAO.findByHallNumber(hall.getHallNumber())!=null){
            throw new IllegalArgumentException("Hall of the given number already exists.");
        }

        if(!hall.getHallAvailableFrom().isBefore(hall.getHallAvailableUntil())){
            throw new IllegalArgumentException("Available form must be before available until.");
        }

        hall.setHallAddedAt(LocalDateTime.now());
        try{
            hallDAO.saveHall(hall);
            System.out.println("Hall added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add hall. ",e);
        }
    }

    public void deleteHall(int hallNumber){
        Hall hallToDelete=hallDAO.findByHallNumber(hallNumber);

        if(hallToDelete==null){
            throw new RuntimeException("Hall to delete is not found.");
        }

        try{
            hallDAO.deleteHall(hallNumber);
            System.out.println("Hall deleted successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete hall. ",e);
        }
    }

    public void editHall(int hallNumber, Hall editedHall) {
        Hall hallToEdit=hallDAO.findByHallNumber(hallNumber);

        if(hallToEdit==null){
            throw new RuntimeException("Hall to edit is not found.");
        }

        if (editedHall.getHallType() != null)
            hallToEdit.setHallType(editedHall.getHallType());

        if (editedHall.getHallCapacity() > 0)
            hallToEdit.setHallCapacity(editedHall.getHallCapacity());

        if (editedHall.getHallPrice() > 0)
            hallToEdit.setHallPrice(editedHall.getHallPrice());

        if (editedHall.getHallAvailableFrom() != null)
            hallToEdit.setHallAvailableFrom(editedHall.getHallAvailableFrom());

        if (editedHall.getHallAvailableUntil() != null)
            hallToEdit.setHallAvailableUntil(editedHall.getHallAvailableUntil());

        if (editedHall.getHallStatus() != null)
            hallToEdit.setHallStatus(editedHall.getHallStatus());

        if (editedHall.getHallRemarks() != null && !editedHall.getHallRemarks().isEmpty())
            hallToEdit.setHallRemarks(editedHall.getHallRemarks());

        try{
            hallDAO.editHall(hallToEdit);
            System.out.println("Hall edited successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to edit hall. ",e);
        }
    }
}

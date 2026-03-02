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
        System.out.println("running hall service before calling hall dao");
        hallDAO.saveHall(hall);
        System.out.println("hall service after calling hall dao");
    }

    public void deleteHall(int hallNumber){
        Hall hallToDelete = hallDAO.findByHallNumber(hallNumber);

        if(hallToDelete==null){
            System.out.println("Hall not found.");
            return;
        }

        List<String> lines=new ArrayList<>();

        try(BufferedReader br=new BufferedReader(new FileReader("data/halls.txt"))){
            String line;
            while((line=br.readLine())!=null){
                lines.add(line);
            }
            System.out.println("halls read successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Iterator<String> iterator= lines.iterator();
        while (iterator.hasNext()){
            String line=iterator.next();
            if (line.contains(String.valueOf(hallNumber))){
                iterator.remove();
                break;
            }
        }
        System.out.println("hall removed");

        try(BufferedWriter bw=new BufferedWriter(new FileWriter("data/halls.txt"))){
            for (String line:lines){
                bw.write(line);
                bw.newLine();
            }
            System.out.println("Updated halls.txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void editHall(int hallNumber, Hall updatedHall) {
        List<Hall> halls = hallDAO.getAllHalls();
        boolean found = false;

        for (Hall hall : halls) {

            if (hall.getHallNumber() == hallNumber) {

                if (updatedHall.getHallType() != null)
                    hall.setHallType(updatedHall.getHallType());

                if (updatedHall.getHallCapacity() > 0)
                    hall.setHallCapacity(updatedHall.getHallCapacity());

                if (updatedHall.getHallPrice() > 0)
                    hall.setHallPrice(updatedHall.getHallPrice());

                if (updatedHall.getHallAvailableFrom() != null)
                    hall.setHallAvailableFrom(updatedHall.getHallAvailableFrom());

                if (updatedHall.getHallAvailableUntil() != null)
                    hall.setHallAvailableUntil(updatedHall.getHallAvailableUntil());

                if (updatedHall.getHallStatus() != null)
                    hall.setHallStatus(updatedHall.getHallStatus());

                if (updatedHall.getHallRemarks() != null && !updatedHall.getHallRemarks().isEmpty())
                    hall.setHallRemarks(updatedHall.getHallRemarks());

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Hall to edit is not found.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/halls.txt"))) {

            for (Hall hall : halls) {
                String line = hall.getHallNumber() + "," +
                        hall.getHallType() + "," +
                        hall.getHallCapacity() + "," +
                        hall.getHallPrice() + "," +
                        hall.getHallAvailableFrom() + "," +
                        hall.getHallAvailableUntil() + "," +
                        hall.getHallStatus() + "," +
                        hall.getHallRemarks() + "," +
                        hall.getHallAddedAt();
                bw.write(line);
                bw.newLine();
            }

            System.out.println("Hall updated successfully.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

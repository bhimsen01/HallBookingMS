package com.hbms.app.dao;

import com.hbms.app.model.Hall;
import com.hbms.app.model.User;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HallDAO {
    private final String file="data/halls.txt";

    private String convertToLine(Hall hall){
        return hall.getHallNumber()+","
                +hall.getHallType()+","
                +hall.getHallCapacity()+","
                +hall.getHallPrice()+","
                +hall.getHallAvailableFrom()+","
                +hall.getHallAvailableUntil()+","
                +hall.getHallStatus()+","
                +hall.getHallRemarks()+","
                +hall.getHallAddedAt();
    }

    public void saveHall(Hall hall){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(file, true))){
            bw.write(convertToLine(hall));
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save hall. ",e);
        }
    }

    public void saveAllHalls(List<Hall> halls) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Hall hall : halls) {
                bw.write(convertToLine(hall));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save halls. ",e);
        }
    }

    public List<Hall> getAllHalls(){
        List<Hall> halls=new ArrayList<>();

        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String line;
            while ((line=br.readLine())!=null){
                if (line.isEmpty()) {
                    System.out.println("Empty line | DAO | getAllHalls");
                    continue;}
                String[] parts=line.split(",");
                Hall hall= new Hall(Integer.parseInt(parts[0]),Hall.HallType.valueOf(parts[1]),Integer.parseInt(parts[2]), Double.parseDouble(parts[3]), LocalTime.parse(parts[4]), LocalTime.parse(parts[5]), Hall.HallStatus.valueOf(parts[6]), parts[7], LocalDateTime.parse(parts[8]));
                halls.add(hall);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to get all halls.",e);
        }
        return halls;
    }

    public Hall findByHallNumber(int hallNumber){
        return getAllHalls().stream().filter(hall -> hall.getHallNumber()==hallNumber).findFirst().orElse(null);
    }

    public void editHall(Hall updatedHall){
        List<Hall> halls=getAllHalls();
        boolean found=false;

        for (int i=0;i<halls.size();i++){
            if(halls.get(i).getHallNumber()==(updatedHall.getHallNumber())){
                halls.set(i, updatedHall);
                found=true;
                break;
            }
        }

        if(!found){
            throw new RuntimeException("Hall not found for update.");
        }

        saveAllHalls(halls);
    }

    public void deleteHall(int hallNumber){
        List<Hall> halls=getAllHalls();

        boolean hallDeleted=halls.removeIf(hall -> hall.getHallNumber()==hallNumber);

        if(!hallDeleted){
            throw new RuntimeException("Hall not found to delete.");
        }

        saveAllHalls(halls);
    }
}

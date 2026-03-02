package com.hbms.app.dao;

import com.hbms.app.model.Hall;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HallDAO {
    private final String file="data/halls.txt";

    public void saveHall(Hall hall){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(file, true))){
            String line= hall.getHallNumber()+","
                    +hall.getHallType()+","
                    +hall.getHallCapacity()+","
                    +hall.getHallPrice()+","
                    +hall.getHallAvailableFrom()+","
                    +hall.getHallAvailableUntil()+","
                    +hall.getHallStatus()+","
                    +hall.getHallRemarks()+","
                    +hall.getHallAddedAt();
            System.out.println("running hall dao");
            bw.write(line);
            bw.newLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Hall> getAllHalls(){
        List<Hall> halls=new ArrayList<>();

        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String line;
            while ((line=br.readLine())!=null){
                String[] parts=line.split(",");
                Hall hall= new Hall(Integer.parseInt(parts[0]),Hall.HallType.valueOf(parts[1]),Integer.parseInt(parts[2]), Double.parseDouble(parts[3]), LocalTime.parse(parts[4]), LocalTime.parse(parts[5]), Hall.HallStatus.valueOf(parts[6]), parts[7], LocalDateTime.parse(parts[8]));
                halls.add(hall);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return halls;
    }

    public Hall findByHallNumber(int hallNumber){
        return getAllHalls().stream().filter(hall -> hall.getHallNumber()==hallNumber).findFirst().orElse(null);
    }
}

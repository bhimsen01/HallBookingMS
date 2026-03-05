package com.hbms.app.controller;

import com.hbms.app.model.Hall;
import com.hbms.app.service.HallService;

public class HallController {
    private final HallService hallService;

    public HallController(HallService hallService){
        this.hallService=hallService;
    }

    public boolean addHall(Hall hall){
        try{
            hallService.addHall(hall);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteHall(int hallNumber){
        try{
            hallService.deleteHall(hallNumber);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editHall(int hallNumber, Hall editedHall){
        try{
            hallService.editHall(hallNumber, editedHall);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

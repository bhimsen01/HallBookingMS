package com.hbms.app.controller;

import com.hbms.app.model.Hall;
import com.hbms.app.service.HallService;

public class HallController {
    private final HallService hallService;

    public HallController(HallService hallService){
        this.hallService=hallService;
    }

    public String addHall(Hall hall){
        try{
            System.out.println("hall controller");
            hallService.addHall(hall);
            System.out.println("hall controller after calling hall service");
            return "Hall added successfully.";
        } catch (Exception e) {
            return "Failed to add hall. "+e.getMessage();
        }
    }
}

//package com.hbms.app.test;
//
//import com.hbms.app.auth.AuthUser;
//import com.hbms.app.controller.HallController;
//import com.hbms.app.dao.HallDAO;
//import com.hbms.app.model.Hall;
//import com.hbms.app.model.User;
//import com.hbms.app.service.HallService;
//import com.hbms.app.session.Session;
//
//import java.time.LocalTime;
//
//public class HallTest {
//    static void main(String[] args) {
//        HallDAO hallDAO=new HallDAO();
//        HallService hallService=new HallService(hallDAO);
//        HallController hallController=new HallController(hallService);
//
//        AuthUser testUser = new AuthUser("U100", "bhimsen", "basnet", "bhimsen@123", User.Role.CUSTOMER);
//        Session.login(testUser);
//
////        Hall hall=new Hall();
////        hall.setHallNumber(2);
////        hall.setHallType(Hall.HallType.AUDITORIUM);
////        hall.setHallCapacity(30);
////        hall.setHallAvailableFrom(LocalTime.parse("09:20"));
////        hall.setHallAvailableUntil(LocalTime.parse("12:50"));
////        hall.setHallPrice(199);
////        hall.setHallStatus(Hall.HallStatus.AVAILABLE);
////        hall.setHallRemarks("hasdasda");
////
////
////        String result = hallController.addHall(hall);
////        System.out.println(result);
//
////        hallService.deleteHall(901);
//
//        Hall updatedHall=new Hall();
//        updatedHall.setHallType(Hall.HallType.BOARDROOM);
//        updatedHall.setHallCapacity(300);
//        updatedHall.setHallAvailableFrom(LocalTime.parse("08:10"));
//        updatedHall.setHallAvailableUntil(LocalTime.parse("11:50"));
//        updatedHall.setHallPrice(9);
//        updatedHall.setHallStatus(Hall.HallStatus.CLOSED);
//        updatedHall.setHallRemarks("qqqqqq");
//        hallService.editHall(2, updatedHall);
//
//        Session.logout();
//    }
//}

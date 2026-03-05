//package com.hbms.app.test;
//
//import com.hbms.app.auth.AuthUser;
//import com.hbms.app.controller.BookingController;
//import com.hbms.app.controller.PaymentController;
//import com.hbms.app.controller.ReceiptController;
//import com.hbms.app.dao.BookingDAO;
//import com.hbms.app.dao.PaymentDAO;
//import com.hbms.app.dao.ReceiptDAO;
//import com.hbms.app.model.User;
//import com.hbms.app.service.BookingService;
//import com.hbms.app.service.PaymentService;
//import com.hbms.app.service.ReceiptService;
//import com.hbms.app.session.Session;
//import com.hbms.app.utility.IdCounter;
//
//public class BookingTest {
//    static void main(String[] args) {
//        IdCounter idCounter = new IdCounter();
//        BookingDAO bookingDAO = new BookingDAO();
//        ReceiptDAO receiptDAO=new ReceiptDAO();
//        PaymentDAO paymentDAO=new PaymentDAO();
//        BookingService bookingService = new BookingService(idCounter, bookingDAO);
//        ReceiptService receiptService=new ReceiptService(idCounter, receiptDAO);
//        ReceiptController receiptController=new ReceiptController(receiptService);
//        PaymentService paymentService=new PaymentService(idCounter, paymentDAO);
//        PaymentController paymentController=new PaymentController(paymentService);
//        BookingController controller = new BookingController(bookingService, receiptController, paymentController);
//
//        AuthUser testUser = new AuthUser("U100", "bhimsen", "basnet", "bhimsen@123", User.Role.CUSTOMER);
//        Session.login(testUser);
//
//        String result = controller.addBooking(
//                "AUDITORIUM",
//                "2",
//                "2026-03-05",
//                "09:20",
//                "10:10",
//                500.0
//        );
//
//        System.out.println(result);
//
////        bookingService.cancelBooking("B102");
//
//        Session.logout();
//    }
//}

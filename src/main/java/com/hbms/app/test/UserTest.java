package com.hbms.app.test;

import com.hbms.app.auth.AuthUser;
import com.hbms.app.controller.UserController;
import com.hbms.app.dao.UserDAO;
import com.hbms.app.model.User;
import com.hbms.app.service.UserService;
import com.hbms.app.session.Session;
import com.hbms.app.utility.IdCounter;

public class UserTest {
    static void main(String[] args) {
        IdCounter idCounter=new IdCounter();
        UserDAO userDAO=new UserDAO();
        UserService userService=new UserService(idCounter, userDAO);
        UserController userController=new UserController(userService);

        AuthUser testUser = new AuthUser("U100", "bhimsen", "basnet", "bhimsen@123", User.Role.CUSTOMER);
        Session.login(testUser);

//        userService.deleteUser("U102");

        User updatedUser=new User();
//        updatedUser.setRole(User.Role.MANAGER);
        updatedUser.setFirstName("Bill");
        updatedUser.setEmail("bill@123");
        userService.updateUserDetails("U101",updatedUser);

        Session.logout();
    }
}

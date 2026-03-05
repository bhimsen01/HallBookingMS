package com.hbms.app.controller;

import com.hbms.app.auth.AuthUser;
import com.hbms.app.model.User;
import com.hbms.app.service.UserService;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    public boolean signup(String firstName, String lastName, String email, String password){
        try{
            userService.signup(firstName, lastName, email, password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public AuthUser login(String email, String password){
        return userService.login(email, password);
    }

    public boolean deleteUser(String userId){
        try{
            userService.deleteUser(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserDetails(String userId, User updatedUser){
        try{
            userService.updateUserDetails(userId,updatedUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserRole(String userId, User updatedUser){
        try{
            userService.updateUserRole(userId, updatedUser);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

package com.hbms.app.controller;

import com.hbms.app.auth.AuthUser;
import com.hbms.app.model.User;
import com.hbms.app.service.UserService;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    public String signup(String firstName, String lastName, String email, String password){
        try{
            userService.signup(firstName, lastName, email, password);
            return "Signup successful.";
        } catch (IllegalArgumentException e){
            return e.getMessage();
        } catch (Exception e) {
            return "Signup failed.";
        }
    }

    public AuthUser login(String email, String password){
        return userService.login(email, password);
    }
}

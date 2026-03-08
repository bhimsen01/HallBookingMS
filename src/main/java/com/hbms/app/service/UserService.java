package com.hbms.app.service;

import com.hbms.app.auth.AuthUser;
import com.hbms.app.dao.UserDAO;
import com.hbms.app.model.User;
import com.hbms.app.utility.IdCounter;
import com.hbms.app.utility.PasswordUtil;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserService {
    private final IdCounter idCounter;
    private final UserDAO userDAO;

    public UserService(IdCounter idCounter, UserDAO userDAO) {
        this.idCounter = idCounter;
        this.userDAO=userDAO;
    }

    public void signup(String firstName, String lastName, String email, String password){
        if (firstName==null || firstName.isBlank())
            throw new IllegalArgumentException("First name is empty.");

        if (lastName==null || lastName.isBlank())
            throw new IllegalArgumentException("Last name is empty.");

        if (email==null || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email.");

        String userId= idCounter.generateId("USER","U");
        String hashedPassword= PasswordUtil.hashPassword(password);
        User.Role role= User.Role.CUSTOMER;
        double balance=0;
        LocalDateTime userCreatedAt=LocalDateTime.now();

        User user=new User(userId, firstName, lastName, email, hashedPassword, role, balance, userCreatedAt);

        try{
            userDAO.saveUser(user);
            System.out.println("Signup successful.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Signup failed. ",e);
        }
    }

    public AuthUser login(String email, String password){
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email is empty.");

        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password is empty.");

        User user=userDAO.findByEmail(email);

        if (user==null)
            throw new IllegalArgumentException("User not found.");

        try{
            if(PasswordUtil.verifyPassword(user.getPassword(), password)){
                return new AuthUser(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());
            }
            throw new IllegalArgumentException("Incorrect password.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Login failed. ",e);
        }
    }

    public void deleteUser(String userId){
        User userToDelete=userDAO.findById(userId);

        if(userToDelete==null){
            throw new RuntimeException("User to delete is not found.");
        }

        try{
            userDAO.deleteUser(userId);
            System.out.println("User deleted.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user. ",e);
        }
    }

    public void updateUserRole(String userId, User updatedUser){
        User userToUpdate=userDAO.findById(userId);

        if(userToUpdate==null){
            throw new RuntimeException("User to update is not found.");
        }

        if(userToUpdate.getRole()!=null){
            userToUpdate.setRole(updatedUser.getRole());
        } else {
            throw new RuntimeException("Role is null.");
        }

        try{
            userDAO.updateUser(userToUpdate);
            System.out.println("User role updated.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user role. ",e);
        }
    }

    public void updateUserDetails(String userId, User updatedUser){
        User userToUpdate=userDAO.findById(userId);

        if (userToUpdate==null){
            throw new RuntimeException("User to update is not found.");
        }

        if (!updatedUser.getFirstName().isEmpty() && updatedUser.getFirstName()!=null){
            userToUpdate.setFirstName(updatedUser.getFirstName());
        }

        if (!updatedUser.getLastName().isEmpty() && updatedUser.getLastName()!=null){
            userToUpdate.setLastName(updatedUser.getLastName());
        }

        if (!updatedUser.getEmail().isEmpty() && updatedUser.getEmail()!=null){
            userToUpdate.setEmail(updatedUser.getEmail());
        }

        if (!updatedUser.getPassword().isEmpty() && updatedUser.getPassword()!=null){
            userToUpdate.setPassword(PasswordUtil.hashPassword(updatedUser.getPassword()));
        }

        try{
            userDAO.updateUser(userToUpdate);
            System.out.println("User details updated.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user details. ",e);
        }
    }
}

package com.hbms.app.service;

import com.hbms.app.auth.AuthUser;
import com.hbms.app.dao.UserDAO;
import com.hbms.app.model.User;
import com.hbms.app.utility.IdCounter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
        User.Role role= User.Role.CUSTOMER;
        double balance=0;
        LocalDateTime userCreatedAt=LocalDateTime.now();


        User user=new User(userId, firstName, lastName, email, password, role, balance, userCreatedAt);

        userDAO.saveUser(user);
    }

    public AuthUser login(String email, String password){
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email is empty.");

        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password is empty.");

        User user=userDAO.findByEmail(email);

        if (user==null)
            throw new IllegalArgumentException("User not found.");

        if (!user.getPassword().equals(password))
            throw new IllegalArgumentException("Incorrect password.");

        return new AuthUser(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());
    }

    public void deleteUser(String userId){
        User userToDelete=userDAO.findById(userId);

        if(userToDelete==null){
            System.out.println("User to delete does not exist");
        } else {
            List<String> lines=new ArrayList<>();

            try(BufferedReader br=new BufferedReader(new FileReader("data/users.txt"))){
                String line;
                while((line=br.readLine())!=null){
                    lines.add(line);
                }
                System.out.println("users read successfully");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Iterator<String> iterator= lines.iterator();
            while (iterator.hasNext()){
                String line=iterator.next();
                if (line.contains(userId)){
                    iterator.remove();
                    break;
                }
            }
            System.out.println("hall removed");

            try(BufferedWriter bw=new BufferedWriter(new FileWriter("data/users.txt"))){
                for (String line:lines){
                    bw.write(line);
                    bw.newLine();
                }
                System.out.println("Updated users.txt");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateUserRole(String userId, User updatedUser){
        List<User> users=userDAO.getAllUsers();
        boolean found=false;

        for(User user:users){
            if (user.getUserId().equals(userId)){
                if(updatedUser.getRole()!=null){
                    user.setRole(updatedUser.getRole());
                }
                found =true;
                break;
            }
        }

        if(!found){
            System.out.println("User to edit not found.");
            return;
        }

        try(BufferedWriter bw=new BufferedWriter(new FileWriter("data/users.txt"))){
            for (User user:users){
                String line=user.getUserId()+","
                        +user.getFirstName()+","
                        +user.getLastName()+","
                        +user.getEmail()+","
                        +user.getPassword()+","
                        +user.getRole()+","
                        +user.getBalance()+","
                        +user.getUserCreatedAt();
                bw.write(line);
                bw.newLine();
            }
            System.out.println("Updated users.txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUserDetails(String userId, User updatedUser){
        List<User> users=userDAO.getAllUsers();
        boolean found=false;

        for(User user:users){
            if (user.getUserId().equals(userId)){
                if(updatedUser.getFirstName()!=null)
                    user.setFirstName(updatedUser.getFirstName());

                if(updatedUser.getLastName()!=null)
                    user.setLastName(updatedUser.getLastName());

                if(updatedUser.getEmail()!=null)
                    user.setEmail(updatedUser.getEmail());

                if(updatedUser.getPassword()!=null)
                    user.setPassword(updatedUser.getPassword());

                found =true;
                break;
            }
        }

        if(!found){
            System.out.println("User to edit not found.");
            return;
        }

        try(BufferedWriter bw=new BufferedWriter(new FileWriter("data/users.txt"))){
            for (User user:users){
                String line=user.getUserId()+","
                        +user.getFirstName()+","
                        +user.getLastName()+","
                        +user.getEmail()+","
                        +user.getPassword()+","
                        +user.getRole()+","
                        +user.getBalance()+","
                        +user.getUserCreatedAt();
                bw.write(line);
                bw.newLine();
            }
            System.out.println("Updated users.txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

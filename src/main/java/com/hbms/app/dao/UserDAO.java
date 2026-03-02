package com.hbms.app.dao;

import com.hbms.app.model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public final String filePath="data/users.txt";

    public void saveUser(User user){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(filePath, true))){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers(){
        List<User> users= new ArrayList<>();

        try(BufferedReader br=new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line =br.readLine())!=null){
                String[] parts=line.split(",");
                User user=new User(parts[0], parts[1], parts[2],parts[3],parts[4],User.Role.valueOf(parts[5]), Double.parseDouble(parts[6]), LocalDateTime.parse(parts[7]));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public User findById(String id){
        return getAllUsers().stream().filter(user -> user.getUserId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public User findByEmail(String email){
        return getAllUsers().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }
}

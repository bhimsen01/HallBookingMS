package com.hbms.app.dao;

import com.hbms.app.model.User;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public final String filePath="data/users.txt";

    private String convertToLine(User user) {
        return user.getUserId() + "," +
                user.getFirstName() + "," +
                user.getLastName() + "," +
                user.getEmail() + "," +
                user.getPassword() + "," +
                user.getRole() + "," +
                user.getBalance() + "," +
                user.getUserCreatedAt();
    }

    public void saveAllUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                bw.write(convertToLine(user));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save users. ",e);
        }
    }

    public void saveUser(User user){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(filePath, true))){
            bw.write(convertToLine(user));
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save user. ",e);
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
        } catch (IOException e) {
            throw new RuntimeException("Failed to get all users. ",e);
        }
        return users;
    }

    public User findById(String id){
        return getAllUsers().stream().filter(user -> user.getUserId().equals(id)).findFirst().orElse(null);
    }

    public User findByEmail(String email){
        return getAllUsers().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    public void deleteUser(String userId){
        List<User> users=getAllUsers();

        boolean userDeleted=users.removeIf(user -> user.getUserId().equals(userId));

        if(!userDeleted){
            throw new RuntimeException("User not found for deletion.");
        }

        saveAllUsers(users);
    }

    public void updateUser(User updatedUser){
        List<User> users=getAllUsers();
        boolean found=false;

        for (int i=0;i<users.size();i++){
            if(users.get(i).getUserId().equals(updatedUser.getUserId())){
                users.set(i, updatedUser);
                found=true;
                break;
            }
        }

        if(!found){
            throw new RuntimeException("User not found for update.");
        }

        saveAllUsers(users);
    }

}

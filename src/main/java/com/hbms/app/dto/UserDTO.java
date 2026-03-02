package com.hbms.app.dto;

import com.hbms.app.model.User;

public class UserDTO {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final User.Role role;
    private final double balance;

    public UserDTO(String firstName, String lastName, String email, User.Role role, double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public User.Role getRole() {
        return role;
    }

    public double getBalance() {
        return balance;
    }
}

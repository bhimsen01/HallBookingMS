package com.hbms.app.auth;

import com.hbms.app.model.User;

public class AuthUser {
    private final String userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final User.Role role;


    public AuthUser(String userId, String firstName, String lastName, String email, User.Role role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public User.Role getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

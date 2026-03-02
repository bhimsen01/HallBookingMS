package com.hbms.app.session;

import com.hbms.app.auth.AuthUser;

public class Session {
    public static AuthUser currentUser;

    private Session(){};

    public static void login(AuthUser user){
        currentUser=user;
    }

    public static AuthUser getCurrentUser() {
        if (currentUser == null) {
            throw new IllegalStateException("No user logged in.");
        }
        return currentUser;
    }

    public static boolean isLoggedIn(){
        return currentUser!=null;
    }

    public static void logout(){
        currentUser = null;
    }
}

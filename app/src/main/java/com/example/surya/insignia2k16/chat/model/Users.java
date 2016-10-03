package com.example.surya.insignia2k16.chat.model;

/**
 * Created by surya on 04-07-2016.
 */
public class Users {

    String userName;
    String email;
    String photoUrl;

    public Users() {
    }

    public Users(String userName, String email,String photoUrl) {
        this.userName = userName;
        this.email = email;
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}

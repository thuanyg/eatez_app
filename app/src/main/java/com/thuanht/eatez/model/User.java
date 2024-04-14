package com.thuanht.eatez.model;

public class User {
    private int userid;
    private String fullName, email, avatar_image;

    public User() {
    }
    public int getUserid() {
        return userid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar_image() {
        return avatar_image;
    }
}

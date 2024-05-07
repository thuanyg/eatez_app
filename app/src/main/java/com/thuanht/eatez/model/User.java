package com.thuanht.eatez.model;

public class User {
    private int userid;
    private String fullName, email, avatar_image;

    public User() {
    }

    public User(int userid, String fullName, String email, String avatar_image) {
        this.userid = userid;
        this.fullName = fullName;
        this.email = email;
        this.avatar_image = avatar_image;
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

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar_image(String avatar_image) {
        this.avatar_image = avatar_image;
    }
}

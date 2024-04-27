package com.thuanht.eatez.model;

import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("userid")
    private int userid;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("avatar_image")
    private String avatar_image;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatar_image() {
        return avatar_image;
    }

    public void setAvatar_image(String avatar_image) {
        this.avatar_image = avatar_image;
    }
}

package com.thuanht.eatez.jsonResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thuanht.eatez.model.User;

public class LoginResponse {
    private boolean status;
    @SerializedName("data")
    @Expose
    private User user;
    private String message;

    public LoginResponse(boolean status,User data, String message) {
        this.status = status;
        this.user = data;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}

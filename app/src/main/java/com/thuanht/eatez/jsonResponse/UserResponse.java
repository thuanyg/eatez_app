package com.thuanht.eatez.jsonResponse;

import com.thuanht.eatez.model.User;

public class UserResponse {
    private boolean status;
    private User data;

    public boolean isStatus() {
        return status;
    }

    public User getData() {
        return data;
    }
}

package com.thuanht.eatez.jsonResponse;

import com.thuanht.eatez.model.Profile;

public class ProfileResponse {
    private boolean status;

    private String message;

    private String avatar_image;
    private Profile profile;

    public Profile getProfile() {
        return profile;
    }

    public ProfileResponse(boolean status, String message, String avatar_image) {
        this.status = status;
        this.message = message;
        this.avatar_image = avatar_image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAvatar_image() {
        return avatar_image;
    }

    public void setAvatar_image(String avatar_image) {
        this.avatar_image = avatar_image;
    }
}

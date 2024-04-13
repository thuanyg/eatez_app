package com.thuanht.eatez.jsonResponse;

public class LoginResponse {
    private boolean status;
    private String message;

    public LoginResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
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
    @Override
    public String toString(){
        return "LoginResponse{" +
                "status=" + status +
                ", message='" + message  + '}';
    }
}

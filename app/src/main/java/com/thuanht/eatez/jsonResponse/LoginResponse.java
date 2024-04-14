package com.thuanht.eatez.jsonResponse;

public class LoginResponse {
    private boolean status;
    private String data;
    private String message;

    public LoginResponse(boolean status,String data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
                "data="+data+
                ", message='" + message  + '}';
    }
}

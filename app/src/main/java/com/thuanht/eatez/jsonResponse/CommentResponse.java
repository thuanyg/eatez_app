package com.thuanht.eatez.jsonResponse;

import com.thuanht.eatez.model.Comment;

import java.util.List;

public class CommentResponse {
    private boolean status;
    private List<Comment> commentList;
    private String message;

    public CommentResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public List<Comment> getData(){
        return commentList;
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

}

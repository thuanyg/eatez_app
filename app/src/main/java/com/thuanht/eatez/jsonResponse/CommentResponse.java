package com.thuanht.eatez.jsonResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thuanht.eatez.model.Comment;
import com.thuanht.eatez.model.Pagination;

import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class CommentResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Comment> data;

    private Pagination pagination;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}

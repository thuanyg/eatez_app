package com.thuanht.eatez.jsonResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thuanht.eatez.model.Comment;

import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class CommentResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Comment> data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }

}

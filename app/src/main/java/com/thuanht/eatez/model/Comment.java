package com.thuanht.eatez.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Comment {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("Content")
    @Expose
    private String content;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Time")
    @Expose
    private String time;
    @SerializedName("PostId")
    @Expose
    private String postId;
    @SerializedName("Name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

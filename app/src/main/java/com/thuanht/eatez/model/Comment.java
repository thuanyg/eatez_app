package com.thuanht.eatez.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Comment {
    @SerializedName("cmt_id")
    @Expose
    private String cmtId;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("avatar_image")
    @Expose
    private String avatarImage;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("date")
    @Expose
    private String date;

    public String getCmtId() {
        return cmtId;
    }

    public void setCmtId(String cmtId) {
        this.cmtId = cmtId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


}

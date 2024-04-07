package com.thuanht.eatez.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Post implements Serializable {
    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("thumbnail_image")
    @Expose
    private Object thumbnailImage;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("dish")
    @Expose
    private Dish dish;
    @SerializedName("Restaurant")
    @Expose
    private Restaurant restaurant;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(Object thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}

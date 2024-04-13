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
    private String thumbnailImage;

    @SerializedName("order_grab")
    @Expose
    private String orderGrab;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("dish")
    @Expose
    private Dish dish;
    @SerializedName("Restaurant")
    @Expose
    private Restaurant restaurant;

    public Post(String postId, String title, String content, String thumbnailImage, String orderGrab) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.thumbnailImage = thumbnailImage;
        this.orderGrab = orderGrab;
    }

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

    public String getThumbnailImage() {
        return thumbnailImage;
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

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getOrderGrab() {
        return orderGrab;
    }

    public void setOrderGrab(String orderGrab) {
        this.orderGrab = orderGrab;
    }
}

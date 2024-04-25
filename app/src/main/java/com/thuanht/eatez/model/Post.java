package com.thuanht.eatez.model;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "Post")
public class Post implements Serializable {

    // Field own room database
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "post_id")
    private transient int post_id;

    @ColumnInfo(name = "date")
    private String date_rb;

    @Ignore
    @SerializedName("post_id")
    @Expose
    private String postId;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("content")
    @Expose
    private String content;

    @ColumnInfo(name = "thumbnail_image")
    @SerializedName("thumbnail_image")
    @Expose
    private String thumbnailImage;

    @ColumnInfo(name = "order_grab")
    @SerializedName("order_grab")
    @Expose
    private String orderGrab;

    @Ignore
    @SerializedName("date")
    @Expose
    private String date;

    @Ignore
    @Embedded
    @SerializedName("dish")
    @Expose
    private Dish dish;
    @Ignore
    @SerializedName("Restaurant")
    @Expose
    private Restaurant restaurant;

    public Post() {
    }

    public Post(String postId, String title, String content, String thumbnailImage, String orderGrab) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.thumbnailImage = thumbnailImage;
        this.orderGrab = orderGrab;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
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

    public String getDate_rb() {
        return date_rb;
    }

    public void setDate_rb(String date_rb) {
        this.date_rb = date_rb;
    }

    public void setOrderGrab(String orderGrab) {
        this.orderGrab = orderGrab;
    }
}

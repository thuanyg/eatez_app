package com.thuanht.eatez.model;

public class Category {
    private int id;
    private int imageID;
    private String imageRes, categoryName;

    public Category(int id, int imageID, String imageRes, String categoryName) {
        this.id = id;
        this.imageID = imageID;
        this.imageRes = imageRes;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getImageRes() {
        return imageRes;
    }

    public void setImageRes(String imageRes) {
        this.imageRes = imageRes;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

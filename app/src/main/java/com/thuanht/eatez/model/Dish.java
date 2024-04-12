package com.thuanht.eatez.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Dish implements Serializable {
    @SerializedName("dish_id")
    @Expose
    private String dishId;
    @SerializedName("dish_name")
    @Expose
    private String dishName;
    @SerializedName("dish_desc")
    @Expose
    private String dishDesc;
    @SerializedName("dish_image")
    @Expose
    private String dishImage;

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishDesc() {
        return dishDesc;
    }

    public void setDishDesc(String dishDesc) {
        this.dishDesc = dishDesc;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }
}

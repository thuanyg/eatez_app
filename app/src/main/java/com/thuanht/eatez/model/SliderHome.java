package com.thuanht.eatez.model;

public class SliderHome {
    private int id;
    private String name, image, link, date;

    public SliderHome(int id, String name, String image, String link, String date) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.link = link;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

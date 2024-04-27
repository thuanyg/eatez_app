package com.thuanht.eatez.database.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Suggestion")
public class Suggestion {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "suggest_value")
    private String suggest_value;

    public Suggestion() {
    }

    public Suggestion(int id, String suggest_value) {
        this.id = id;
        this.suggest_value = suggest_value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSuggest_value() {
        return suggest_value;
    }

    public void setSuggest_value(String suggest_value) {
        this.suggest_value = suggest_value;
    }
}

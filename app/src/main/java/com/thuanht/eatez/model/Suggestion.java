package com.thuanht.eatez.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Suggestion {
    @SerializedName("suggest_value")
    @Expose
    private String suggestValue;

    public String getSuggestValue() {
        return suggestValue;
    }

    public void setSuggestValue(String suggestValue) {
        this.suggestValue = suggestValue;
    }
}

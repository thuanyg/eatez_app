package com.thuanht.eatez.jsonResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thuanht.eatez.model.Suggestion;

import java.util.List;

public class SuggestResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    private List<Suggestion> data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Suggestion> getData() {
        return data;
    }

    public void setData(List<Suggestion> data) {
        this.data = data;
    }
}

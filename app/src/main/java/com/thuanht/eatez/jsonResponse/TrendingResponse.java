package com.thuanht.eatez.jsonResponse;

import com.thuanht.eatez.model.Trending;

import java.util.List;

public class TrendingResponse {
    private boolean status;
    private List<Trending> data;

    public boolean getStatus() {
        return status;
    }

    public List<Trending> getData() {
        return data;
    }
}

package com.thuanht.eatez.jsonResponse;

import com.thuanht.eatez.model.SliderHome;

import java.util.List;

public class SliderResponse {
    private boolean status;
    private List<SliderHome> data;

    public boolean getStatus() {
        return status;
    }

    public List<SliderHome> getData() {
        return data;
    }
}

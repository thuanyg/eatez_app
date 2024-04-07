package com.thuanht.eatez.jsonResponse;

import com.thuanht.eatez.model.Category;
import com.thuanht.eatez.model.Post;

import java.util.List;

public class CategoryResponse {
    private boolean status;
    private List<Category> data;

    public boolean getStatus() {
        return status;
    }

    public List<Category> getData() {
        return data;
    }
}

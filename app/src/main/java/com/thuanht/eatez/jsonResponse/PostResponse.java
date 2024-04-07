package com.thuanht.eatez.jsonResponse;

import com.thuanht.eatez.model.Post;

import java.util.List;

public class PostResponse {
    private boolean status;
    private List<Post> data;

    public boolean getStatus() {
        return status;
    }

    public List<Post> getData() {
        return data;
    }
}

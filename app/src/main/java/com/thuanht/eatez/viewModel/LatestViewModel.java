package com.thuanht.eatez.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thuanht.eatez.model.Post;

import java.util.List;

public class LatestViewModel extends ViewModel {
    private MutableLiveData<List<Post>> posts = new MutableLiveData<>();

    public MutableLiveData<List<Post>> getPosts() {
        return posts;
    }

    public void fetchLatestPosts(){


    }

}

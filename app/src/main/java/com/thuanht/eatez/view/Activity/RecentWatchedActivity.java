package com.thuanht.eatez.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.thuanht.eatez.Adapter.PostCategoryAdapter;
import com.thuanht.eatez.Adapter.PostFavouriteAdapter;
import com.thuanht.eatez.R;
import com.thuanht.eatez.database.database.AppDatabase;
import com.thuanht.eatez.databinding.ActivityRecentWatchedBinding;
import com.thuanht.eatez.model.Post;

import java.util.ArrayList;
import java.util.List;

public class RecentWatchedActivity extends AppCompatActivity {

    private List<Post> posts = new ArrayList<>();
    private ActivityRecentWatchedBinding binding;
    private PostCategoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecentWatchedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();
        eventHandle();
    }

    private void eventHandle() {
        binding.toolbarPostRecently.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void initData() {
        posts = AppDatabase.getInstance(this).postDAO().selectAll();
        if(posts.size() == 0){
            binding.rcvPostWatched.setVisibility(View.GONE);
            binding.tvNoDataPostWatched.setVisibility(View.VISIBLE);
            return;
        }
        adapter = new PostCategoryAdapter(posts, this, post -> {
            goToPostDetailActivity(post.getPost_id());
        });
        binding.rcvPostWatched.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rcvPostWatched.setAdapter(adapter);
    }

    private void goToPostDetailActivity(int postid) {
        Intent intent = new Intent(this, PostDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("postid", postid);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
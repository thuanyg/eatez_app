package com.thuanht.eatez.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.thuanht.eatez.Adapter.PostTrendingAdapter;
import com.thuanht.eatez.Adapter.TrendingAdapter;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivityTrendingBinding;
import com.thuanht.eatez.model.Category;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.model.Trending;
import com.thuanht.eatez.viewModel.HomeViewModel;
import com.thuanht.eatez.viewModel.PostTrendViewModel;

import java.util.ArrayList;
import java.util.List;

public class TrendingActivity extends AppCompatActivity {
    private final String KEY_DISH_TREND_OBJECT = "trend_obj_action_intent";
    private ActivityTrendingBinding binding;
    private List<Post> posts = new ArrayList<>();
    private PostTrendingAdapter adapter;
    private PostTrendViewModel viewModel;
    private Trending t;
    private boolean isLastPage;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrendingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(PostTrendViewModel.class);
        getDataIntent();
        initData();
        eventHandler();
    }

    private void eventHandler() {
        binding.toolbarPostTrending.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null && bundle.containsKey(KEY_DISH_TREND_OBJECT)) {
                try {
                    Trending trending = (Trending) bundle.getSerializable(KEY_DISH_TREND_OBJECT);
                    if(trending != null){
                        t = trending;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


    private void initData() {
        binding.rcvTrending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        viewModel.getPosts().observe(this, post -> {
            if (post != null) {
                this.posts.addAll(post);
                adapter = new PostTrendingAdapter(this.posts, this, p -> {
                    goToPostDetailActivity(Integer.parseInt(p.getPostId()));
                });
                binding.rcvTrending.setAdapter(adapter);
            } else {
                binding.tvNoPostTrending.setVisibility(View.VISIBLE);
            }
            binding.progressPostTrend.setVisibility(View.GONE);
        });

        try {
            viewModel.fetchPostTrends(t.getDish_id(), 1);
            binding.toolbarPostTrending.setTitle(t.getDish_name());
            Glide.with(this).load(t.getImage()).into(binding.imageDishTrend);
        } catch (Exception e){
            e.getMessage();
        }

    }

    private void goToPostDetailActivity(int postid) {
        Intent intent = new Intent(this, PostDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("postid", postid);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
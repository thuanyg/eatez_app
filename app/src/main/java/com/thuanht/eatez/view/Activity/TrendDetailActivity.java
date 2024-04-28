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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.thuanht.eatez.Adapter.TrendingAdapter;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivityTrendDetailBinding;
import com.thuanht.eatez.model.Trending;
import com.thuanht.eatez.viewModel.HomeViewModel;
import com.thuanht.eatez.viewModel.PostTrendViewModel;

import java.util.ArrayList;
import java.util.List;

public class TrendDetailActivity extends AppCompatActivity {
    private ActivityTrendDetailBinding binding;
    private List<Trending> list;
    private TrendingAdapter adapter;
    private HomeViewModel viewModel;
    private final String KEY_DISH_TREND_OBJECT = "trend_obj_action_intent";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrendDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        initUI();
        initData();
        eventHandler();
    }

    private void eventHandler() {
        binding.btnBackTrendDetail.setOnClickListener(v -> finish());
    }

    private void initUI() {
        list = new ArrayList<>();
        adapter = new TrendingAdapter(list, trending -> {
            // Send dish (Object) -> Trending Activity
            Intent intent = new Intent(this, TrendingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_DISH_TREND_OBJECT, trending);
            intent.putExtras(bundle);
            startActivity(intent);
        }, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        binding.rcvAllTrend.setLayoutManager(gridLayoutManager);
//        binding.rcvAllTrend.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.rcvAllTrend.setAdapter(adapter);
    }

    private void initData() {
        viewModel.getTrends().observe(this, trendings -> {
            if (trendings != null) {
                this.list.clear();
                this.list.addAll(trendings);
                adapter.notifyDataSetChanged();
                binding.progressTrendDetail.setVisibility(View.GONE);
            }
        });
        viewModel.fetchTrending();
    }


}
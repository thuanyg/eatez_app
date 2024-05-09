package com.thuanht.eatez.view.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.thuanht.eatez.Adapter.PostCategoryAdapter;
import com.thuanht.eatez.app.MyBroadcastReceiver;
import com.thuanht.eatez.databinding.ActivityPostCategoryBinding;
import com.thuanht.eatez.model.Category;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.viewModel.PostCategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class PostCategoryActivity extends AppCompatActivity {
    private final String KEY_CATEGORY_ID = "category_id_action_intent";
    private MyBroadcastReceiver broadcastReceiver;
    private ActivityPostCategoryBinding binding;
    private List<Post> posts;
    private PostCategoryAdapter adapter;
    private PostCategoryViewModel viewModel;
    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int cid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUI();
        initRecyclerView();
        initDataRecyclerView();
        eventHandler();
        setStatusLastPage();
    }

    public void initUI(){
        viewModel = new ViewModelProvider(this).get(PostCategoryViewModel.class);
        broadcastReceiver = new MyBroadcastReceiver();
    }
    private void eventHandler() {
        binding.btnBackCategory.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        binding.nestedScrollViewPostCategory.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                LoadMoreData();
            }
        });

        binding.btnTryAgain.setOnClickListener(v -> {
            binding.layoutDisconnect.setVisibility(View.GONE);
            binding.nestedScrollViewPostCategory.setVisibility(View.VISIBLE);
            binding.progressPostCategory.setVisibility(View.VISIBLE);
            fetchPostCategory();
        });
    }

    private void LoadMoreData() {
        if(isLastPage) {
            binding.tvDataLoadedPostCategory.setVisibility(View.VISIBLE);
            return;
        };
        if(!isLoading){
            currentPage++;
            viewModel.fetchPostOfCategory(this, cid, currentPage);
        }
    }


    private void initRecyclerView() {
        posts = new ArrayList<>();
        adapter = new PostCategoryAdapter(posts, this, post -> {
            int id = Integer.parseInt(post.getPostId());
            goToPostDetailActivity(id);
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rcvPostWithCategory.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.rcvPostWithCategory.addItemDecoration(itemDecoration);
        binding.rcvPostWithCategory.setAdapter(adapter);
    }
    private void goToPostDetailActivity(int postid) {
        Intent intent = new Intent(this, PostDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("postid", postid);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void initDataRecyclerView() {
        isLoading = true;
        binding.progressPostCategory.setVisibility(View.VISIBLE);
        viewModel.getIsNetworkDisconnect().observe(this, isNetwordDisconnect -> {
            binding.layoutDisconnect.setVisibility(View.VISIBLE);
            binding.nestedScrollViewPostCategory.setVisibility(View.GONE);
            binding.progressPostCategory.setVisibility(View.GONE);
        });
        viewModel.getPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> list) {
                isLoading = true;
                if (list == null) {
                    binding.tvNoDataPostCategory.setVisibility(View.VISIBLE);
                    binding.progressPostCategory.setVisibility(View.GONE);
                    return;
                }
                if (posts.isEmpty()) {
                    posts.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    // Nếu danh sách posts đã chứa dữ liệu, thêm dữ liệu mới vào và cập nhật RecyclerView
                    int startPosition = posts.size();
                    posts.addAll(list);
                    adapter.notifyItemRangeInserted(startPosition, list.size());
                }
                isLoading = false;
                binding.progressPostCategory.setVisibility(View.GONE);
            }
        });
        fetchPostCategory();
    }

    private void fetchPostCategory(){
        Category category = null;
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null && bundle.containsKey(KEY_CATEGORY_ID)) {
                category = (Category) bundle.getSerializable(KEY_CATEGORY_ID);
                cid = category.getId();
                viewModel.fetchPostOfCategory(this, cid, 1);
                binding.collapsPostCategory.setTitle(category.getCname());
            }
        }
    }

    public void setStatusLastPage(){
        viewModel.getIsLastPage().observe(this, aBoolean -> {
            this.isLastPage = aBoolean;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
}
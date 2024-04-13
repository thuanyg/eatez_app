package com.thuanht.eatez.view.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.TokenWatcher;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivityPostDetailBinding;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.utils.DateUtils;
import com.thuanht.eatez.view.Dialog.LoadingDialog;
import com.thuanht.eatez.viewModel.PostDetailViewModel;

public class PostDetailActivity extends AppCompatActivity {
    private ActivityPostDetailBinding binding;
    private PostDetailViewModel viewModel;
    protected LoadingDialog loadingDialog;
    private int postid;
    private String orderLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(PostDetailViewModel.class);
        loadingDialog = new LoadingDialog(this);

        // Get post id
        Intent intent = getIntent();
        if (intent != null) {
            postid = intent.getIntExtra("postid", 0);
        }

        initUI();
        initData();
        eventHandler();
        setContentView(binding.getRoot());
    }

    public void initUI(){
        setSupportActionBar(binding.toolbarFavourite);
        getSupportActionBar().show();

        binding.layoutPostDetail.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > 1000) {
                binding.favRelativePostDetail.setVisibility(View.GONE);
            } else {
                binding.favRelativePostDetail.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initData() {
        loadingDialog.show();
        viewModel.getPost().observe(this, new Observer<Post>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(Post post) {
                if (post != null) {
                    orderLink = post.getOrderGrab();
                    RenderDataOnUI(post);
                }
                loadingDialog.hide();
                binding.layoutPostDetail.setVisibility(View.VISIBLE);
            }
        });
        viewModel.fetchPostDetail(postid);
    }

    // Render data
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void RenderDataOnUI(@NonNull Post post) {
        binding.titlePostDetail.setText(post.getTitle());
        CharSequence spanned = HtmlCompat.fromHtml(post.getContent(), HtmlCompat.FROM_HTML_MODE_LEGACY);
        binding.tvContentPostDetail.setText(spanned);
        binding.tvDatePostDetail.setText(DateUtils.convertToRelativeTime(post.getDate()));
        Glide.with(this).load(post.getThumbnailImage()).into(binding.imagePostDetail);
    }

    public void eventHandler() {
        binding.toolbarFavourite.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(PostDetailActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // Save post into favourite list
        binding.toolbarFavourite.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.btn_favouritePost){
                changeFavouriteIcon(item);

            }
            return false;
        });

        // Redirect to Grab/Shoppefood
        binding.fabItemGrab.setOnClickListener(v -> {
            if (orderLink.length() > 0) {
                Uri uri = Uri.parse(orderLink);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

    }

    private void changeFavouriteIcon(MenuItem item) {
        if (item.getIcon().getColorFilter() != null && item.getIcon().getColorFilter()
                .equals(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN))) {
            item.getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        } else {
            item.getIcon().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_fav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
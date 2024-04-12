package com.thuanht.eatez.view.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bumptech.glide.Glide;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(PostDetailViewModel.class);
        loadingDialog = new LoadingDialog(this);

//        Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                loadingDialog.cancel();
//            }
//        };
//        handler.postDelayed(runnable, 1000);


        Intent intent = getIntent();
        if (intent != null) {
            postid = intent.getIntExtra("postid", 0);
//                binding.titlePostDetail.setText(post.getTitle());
//                CharSequence spanned = HtmlCompat.fromHtml(post.getContent(), HtmlCompat.FROM_HTML_MODE_LEGACY);
//                binding.contentPostDetail.setText(spanned);
//                Glide.with(this).load(post.getThumbnailImage()).into(binding.imagePostDetail);
        }
        initData();
        eventHandler();
        setContentView(binding.getRoot());
    }

    private void initData() {
        loadingDialog.show();
        viewModel.getPost().observe(this, new Observer<Post>() {
            @Override
            public void onChanged(Post post) {
                if (post != null) {
                    RenderDataOnUI(post);
                }
                loadingDialog.hide();
            }
        });
        viewModel.fetchPostDetail(postid);
    }

    private void RenderDataOnUI(@NonNull Post post) {
        binding.titlePostDetail.setText(post.getTitle());
        CharSequence spanned = HtmlCompat.fromHtml(post.getContent(), HtmlCompat.FROM_HTML_MODE_LEGACY);
        binding.contentPostDetail.setText(spanned);
        Glide.with(this).load(post.getThumbnailImage()).into(binding.imagePostDetail);
        binding.authorPostDetail.setText("Thuan.HT");
        binding.datePostDetail.setText(DateUtils.getInstance().FormatDateStringToDayMonth(post.getDate()));
    }

    public void eventHandler() {
        binding.btnBackPostDetail.setOnClickListener(v -> {
            Intent intent = new Intent(PostDetailActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}
package com.thuanht.eatez.view.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.thuanht.eatez.databinding.ActivityPostDetailBinding;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.view.Dialog.LoadingDialog;

public class PostDetailActivity extends AppCompatActivity {
    private ActivityPostDetailBinding binding;
    protected LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailBinding.inflate(getLayoutInflater());

        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        Handler handler = new Handler();
        Runnable runnable  = new Runnable() {
            @Override
            public void run() {
                loadingDialog.cancel();
            }
        };
        handler.postDelayed(runnable, 2000);


        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null && bundle.containsKey("post")) {
                Post post = (Post) bundle.getSerializable("post");
                binding.titlePostDetail.setText(post.getTitle());
                binding.contentPostDetail.setText(post.getContent());
            }
        }

        eventHandler();
        setContentView(binding.getRoot());
    }

    public void eventHandler(){
        binding.btnBackPostDetail.setOnClickListener(v -> {
            Intent intent = new Intent(PostDetailActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}
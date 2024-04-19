package com.thuanht.eatez.view.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.thuanht.eatez.Adapter.CommentAdapter;
import com.thuanht.eatez.LocalData.LocalDataManager;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivityPostDetailBinding;
import com.thuanht.eatez.interfaceEvent.CommentCallback;
import com.thuanht.eatez.model.Comment;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.utils.DateUtils;
import com.thuanht.eatez.view.Dialog.DialogUtil;
import com.thuanht.eatez.view.Dialog.LoadingDialog;
import com.thuanht.eatez.viewModel.PostDetailViewModel;
import com.thuanht.eatez.viewModel.SavePostViewModel;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity implements CommentCallback {
    private ActivityPostDetailBinding binding;
    private PostDetailViewModel viewModel;
    private SavePostViewModel savePostViewModel;
    private CommentAdapter commentAdapter;
    private RecyclerView recyclerView;
    private List<Comment> comments;
    protected LoadingDialog loadingDialog;
    private int postid, userid;
    private String orderLink;
    private Boolean isSaved = false;
    private boolean isUnSaved = false;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(PostDetailViewModel.class);
        savePostViewModel = new ViewModelProvider(this).get(SavePostViewModel.class);
        loadingDialog = new LoadingDialog(this);



        // Get post id
        Intent intent = getIntent();
        if (intent != null) {
            postid = intent.getIntExtra("postid", 0);
        }
        // Get user id
        userid = LocalDataManager.getInstance().getUserLogin().getUserid();



        savePostProcess();
        initUI();
        initData();

        // Comment
        initRecyclerView();
        initDataRecyclerView();
        eventHandler();
        setContentView(binding.getRoot());
    }

    private void savePostProcess() {
        // Check xem da luu truoc do chua
        savePostViewModel.getIsUserSavedBefore().observe(this, aBoolean -> {
            isSaved = aBoolean;
            if (isSaved) {
//                Toast.makeText(this, "You have saved thís post before", Toast.LENGTH_SHORT).show();
                if (mMenu != null) {
                    MenuItem item = mMenu.findItem(R.id.btn_favouritePost);
                    item.setIcon(R.drawable.saved_favourite);
                }
            } else {
                if (mMenu != null) {
                    MenuItem item = mMenu.findItem(R.id.btn_favouritePost);
                    item.setIcon(R.drawable.add_to_favorite_);
                }
            }
        });
        savePostViewModel.checkSaveBefore(userid, postid);

        // Luu bai viet
        savePostViewModel.getIsSaveSuccess().observe(this, aBoolean -> {
            if (aBoolean) {
                isSaved = true;
                DialogUtil.showSuccessDialog(this, "Save completed",
                        "Please check your favorites list to see all saved posts.", "Ok");
            } else {
                isSaved = false;
                DialogUtil.showErrorDalog(this, null, null, null);
            }
        });

        // Xoa bai viet da luu
        savePostViewModel.getIsUnSaveSuccess().observe(this, aBoolean -> {
            if (aBoolean) {
                isUnSaved = true;
                DialogUtil.showSuccessDialog(this, "Unsave completed",
                        "You have successfully completed the task", "Ok");
            } else {
                isUnSaved = false;
                DialogUtil.showErrorDalog(this, null, null, null);
            }
        });
    }


    public void initUI() {
        setSupportActionBar(binding.toolbarFavourite);
        getSupportActionBar().show();

        binding.layoutPostDetail.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
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
                loadingDialog.cancel();
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
            if (item.getItemId() == R.id.btn_favouritePost) {
                if (!isSaved) {
                    savePostViewModel.savePost(userid, postid);
                    item.setIcon(R.drawable.saved_favourite);
                } else {
                    savePostViewModel.unSavePost(userid, postid);
                    isSaved = false;
                    item.setIcon(R.drawable.add_to_favorite_);
                }
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

        binding.btnSubmitComment.setOnClickListener(v -> {
            String content = binding.txtComment.getText().toString();
            if(viewModel.varidate(content)){
                viewModel.addComment(userid,postid,content);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_fav_menu, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    private void initRecyclerView(){
        comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, comments);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rcvComment.setLayoutManager(layoutManager);
        binding.rcvComment.setAdapter(commentAdapter);
    }

    private void initDataRecyclerView(){
        viewModel.getComments().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> commentList) {
                if(commentList == null) {
                    binding.tvNoComment.setVisibility(View.VISIBLE);
                    return;
                }
                if (comments.size() > 0) {
                    comments.addAll(commentList);
                    commentAdapter.notifyDataSetChanged();
                }else {
                    int startPosition = comments.size();
                    comments.addAll(commentList);
                    commentAdapter.notifyItemRangeInserted(startPosition,commentList.size());
                }
                loadingDialog.cancel();
                binding.tvNoComment.setVisibility(View.GONE);
            }
        });
        viewModel.fetchComments(postid);
    }

    @Override
    public void onCommentSuccess() {
        viewModel.fetchComments(postid);
    }

    @Override
    public void onCommentFailure(String errorMessage) {
        Toast.makeText(this,"Comment fail to sumit", Toast.LENGTH_SHORT).show();
    }
}
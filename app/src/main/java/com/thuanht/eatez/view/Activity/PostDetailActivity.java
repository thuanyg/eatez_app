package com.thuanht.eatez.view.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.thuanht.eatez.Adapter.CommentAdapter;
import com.thuanht.eatez.LocalData.LocalDataManager;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivityPostDetailBinding;
import com.thuanht.eatez.interfaceEvent.CommentCallback;
import com.thuanht.eatez.model.Comment;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.model.User;
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
    private List<Comment> comments;
    private int postid, userid;
    private String orderLink;
    private Boolean isSaved = false;
    private boolean isUnSaved = false;
    private Menu mMenu;
    private int pageNumber = 1;
    private boolean isLastPageComment = false;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(PostDetailViewModel.class);
        savePostViewModel = new ViewModelProvider(this).get(SavePostViewModel.class);
        viewModel.setCommentCallback(this);

        // Get post id
        Intent intent = getIntent();
        if (intent != null) {
            postid = intent.getIntExtra("postid", 0);
        }
        // Get user id
        userid = LocalDataManager.getInstance().getUserLogin().getUserid();


        savePostProcess();
        initUI();
        // Initial post detail
        initData();
        // Comment
        initRecyclerViewComment();
        initDataRecyclerViewComment();

        eventHandler();
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
    }

    private void initData() {
        viewModel.getPost().observe(this, new Observer<Post>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(Post post) {
                if (post != null) {
                    RenderDataPostDetailOnUI(post);
                }
                binding.progressPostDetail.setVisibility(View.GONE);
                binding.layoutPostDetail.setVisibility(View.VISIBLE);
                binding.favRelativePostDetail.setVisibility(View.VISIBLE);
            }
        });
        viewModel.fetchPostDetail(postid);
    }

    // Render data
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void RenderDataPostDetailOnUI(@NonNull Post post) {
        orderLink = post.getOrderGrab();
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

        // Add comment
        binding.btnSubmitComment.setOnClickListener(v -> {
            String content = binding.txtComment.getText().toString();
            float rating = binding.simpleRatingBar.getRating();
            if (viewModel.varidate(content)) {
                binding.btnSubmitComment.startAnimation();
                viewModel.addComment(userid, postid, content, (int) rating);
            }
        });

        // Load more comment
        binding.layoutPostDetail.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > 700) {
                    binding.favRelativePostDetail.setVisibility(View.GONE);
                } else {
                    binding.favRelativePostDetail.setVisibility(View.VISIBLE);
                }
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    LoadMoreComment();
                }
            }
        });

    }

    private void LoadMoreComment() {
        if (isLastPageComment) {
            binding.progressBarLoadComment.setVisibility(View.GONE);
            return;
        }
        if (!isLoading) {
            binding.progressBarLoadComment.setVisibility(View.VISIBLE);
            this.pageNumber++;
            viewModel.fetchComments(postid, pageNumber);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_fav_menu, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    private void initRecyclerViewComment() {
        comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, comments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rcvComment.setLayoutManager(layoutManager);
        binding.rcvComment.setAdapter(commentAdapter);
    }

    private void initDataRecyclerViewComment() {
        viewModel.getIsLastPageComment().observe(this, aBoolean -> this.isLastPageComment = aBoolean);
        isLoading = true;
        viewModel.getComments().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> commentList) {
                if (commentList == null) {
                    binding.tvNoComment.setVisibility(View.VISIBLE);
                    binding.progressBarLoadComment.setVisibility(View.GONE);
                    return;
                }
                if (comments.isEmpty()) {
                    comments.addAll(commentList);
                    commentAdapter.notifyDataSetChanged();
                } else {
                    int startPosition = comments.size();
                    comments.addAll(commentList);
                    commentAdapter.notifyItemRangeInserted(startPosition, commentList.size());
                }
                isLoading = false;
                binding.tvNoComment.setVisibility(View.GONE);
                binding.progressBarLoadComment.setVisibility(View.GONE);
            }
        });
        viewModel.fetchComments(postid, 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCommentSuccess() {
        Comment c = new Comment();
        User u = LocalDataManager.getInstance().getUserLogin();

        c.setFullName(u.getFullName());
        c.setAvatarImage(u.getAvatar_image());
        c.setContent(binding.txtComment.getText().toString());
        c.setRating(String.valueOf(binding.simpleRatingBar.getRating()));
        c.setDate("Just now");

        comments.add(0, c);
        commentAdapter.notifyItemInserted(0);

        binding.txtComment.setText("");
        binding.btnSubmitComment.revertAnimation();
    }

    @Override
    public void onCommentFailure(String errorMessage) {
        Toast.makeText(this, "Comment fail to submit", Toast.LENGTH_SHORT).show();
    }
}
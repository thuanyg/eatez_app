package com.thuanht.eatez.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thuanht.eatez.databinding.ItemCommentBinding;
import com.thuanht.eatez.model.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder>{
    private List<Comment> commentList;
    private Context context;
    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }
    public void setData(List<Comment> comments) {
        this.commentList = comments;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemCommentBinding itemCommentBinding = ItemCommentBinding.inflate(LayoutInflater.from(context),parent,false);
        return new CommentAdapter.MyViewHolder(itemCommentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.binding.tvNameUserComment.setText(comment.getFullName());
        holder.binding.DateUserComment.setText(comment.getDate());
        holder.binding.ContentUserComment.setText(comment.getContent());
        holder.binding.RatingBarUserComment.setRating(Float.parseFloat(comment.getRating()));
        if (comment.getAvatarImage() != null && !comment.getAvatarImage().isEmpty()) {
            Glide.with(context)
                    .load(comment.getAvatarImage())
                    .into(holder.binding.tvAvatarUserComment);
            holder.binding.tvAvatarUserComment.setVisibility(View.VISIBLE);
        } else {
            holder.binding.tvAvatarUserComment.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemCommentBinding binding;

        public MyViewHolder(@NonNull ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

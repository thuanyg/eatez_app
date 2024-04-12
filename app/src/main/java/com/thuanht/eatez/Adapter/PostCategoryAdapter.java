package com.thuanht.eatez.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ItemPostCategoryBinding;
import com.thuanht.eatez.model.Post;

import java.util.List;

public class PostCategoryAdapter extends RecyclerView.Adapter<PostCategoryAdapter.MyViewHolder>{
    private List<Post> posts;
    private Context context;

    public PostCategoryAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostCategoryBinding itemPostCategoryBinding = ItemPostCategoryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(itemPostCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post p = posts.get(position);
        holder.itemPostCategoryBinding.titlePostCategory.setText(p.getTitle());
        CharSequence spanned = HtmlCompat.fromHtml(p.getContent(), HtmlCompat.FROM_HTML_MODE_LEGACY);
        holder.itemPostCategoryBinding.descPostCategory.setText(spanned);
        Glide.with(context).load(p.getThumbnailImage()).into(holder.itemPostCategoryBinding.imagePostCategory);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ItemPostCategoryBinding itemPostCategoryBinding;

        public MyViewHolder(@NonNull ItemPostCategoryBinding itemPostCategoryBinding) {
            super(itemPostCategoryBinding.getRoot());
            this.itemPostCategoryBinding = itemPostCategoryBinding;
        }
    }
}

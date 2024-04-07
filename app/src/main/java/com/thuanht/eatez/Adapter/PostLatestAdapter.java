package com.thuanht.eatez.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.thuanht.eatez.R;
import com.thuanht.eatez.model.Post;

import java.util.List;

public class PostLatestAdapter extends RecyclerView.Adapter<PostLatestAdapter.MyViewHolder> {
    private List<Post> posts;
    private Context context;

    public PostLatestAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_latest, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post p = posts.get(position);
        holder.tvTitle.setText(p.getTitle());
        holder.tvDesc.setText(p.getContent());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle, tvDesc;
        private RoundedImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.titlePost_latest);
            tvDesc = itemView.findViewById(R.id.descPost_latest);
            imageView = itemView.findViewById(R.id.imagePost_latest);
        }
    }
}

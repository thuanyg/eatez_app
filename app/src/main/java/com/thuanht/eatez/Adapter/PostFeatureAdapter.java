package com.thuanht.eatez.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thuanht.eatez.R;
import com.thuanht.eatez.model.Post;

import java.util.List;

public class PostFeatureAdapter extends RecyclerView.Adapter<PostFeatureAdapter.MyViewHolder>{

    private List<Post> postList;

    public PostFeatureAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_review, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post p = postList.get(position);
        holder.tvTitle.setText(p.getTitle());
        holder.tvDesc.setText(p.getDetail());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView tvTitle, tvDesc, tvDate, tvRateNumber;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imagePost);
            tvTitle = itemView.findViewById(R.id.titlePost);
            tvDesc = itemView.findViewById(R.id.descPost);
            tvDate = itemView.findViewById(R.id.date);
            tvRateNumber = itemView.findViewById(R.id.rateNumber);
        }
    }

    public void setData(List<Post> newDataList) {
        if (!postList.equals(newDataList)) {
            this.postList = newDataList;
        }
    }

}

package com.thuanht.eatez.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thuanht.eatez.R;
import com.thuanht.eatez.interfaceEvent.onClickItemListener;
import com.thuanht.eatez.model.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostFeatureAdapter extends RecyclerView.Adapter<PostFeatureAdapter.MyViewHolder>{

    private List<Post> postList;
    private final onClickItemListener<Post> itemListener;

    public PostFeatureAdapter(List<Post> postList, onClickItemListener<Post> listener) {
        this.postList = postList;
        this.itemListener = listener;
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
        holder.tvDesc.setText(p.getContent());

        String formattedDate = p.getDate();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
        try {
            Date date = inputFormat.parse(p.getDate());
            assert date != null;
            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvDate.setText(formattedDate);
        holder.layout.setOnClickListener(v -> itemListener.onClick(postList.get(position)));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final LinearLayout layout;
        private final TextView tvTitle;
        private final TextView tvDesc;
        private final TextView tvDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageView image = itemView.findViewById(R.id.imagePost);
            tvTitle = itemView.findViewById(R.id.titlePost);
            tvDesc = itemView.findViewById(R.id.descPost);
            tvDate = itemView.findViewById(R.id.date);
            TextView tvRateNumber = itemView.findViewById(R.id.rateNumber);
            layout = itemView.findViewById(R.id.layout_post_review);

        }
    }

    public void setData(List<Post> newDataList) {
        if (!postList.equals(newDataList)) {
            this.postList = newDataList;
            notifyDataSetChanged();
        }
    }

}

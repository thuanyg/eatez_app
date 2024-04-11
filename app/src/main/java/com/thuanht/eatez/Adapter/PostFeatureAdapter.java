package com.thuanht.eatez.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thuanht.eatez.R;
import com.thuanht.eatez.interfaceEvent.onClickItemListener;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostFeatureAdapter extends RecyclerView.Adapter<PostFeatureAdapter.MyViewHolder>{

    private List<Post> postList;

    private Context context;
    private final onClickItemListener<Post> itemListener;

    public PostFeatureAdapter(Context context, List<Post> postList, onClickItemListener<Post> listener) {
        this.postList = postList;
        this.itemListener = listener;
        this.context = context;
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
        CharSequence spanned = HtmlCompat.fromHtml(p.getContent(), HtmlCompat.FROM_HTML_MODE_LEGACY);
        holder.tvDesc.setText(spanned);
        String formattedDate = DateUtils.getInstance().FormatDateStringToDayMonth(p.getDate());
        holder.tvDate.setText(formattedDate);

        Glide.with(context)
                .load(p.getThumbnailImage())
                .into(holder.imageView);

        holder.layout.setOnClickListener(v -> itemListener.onClick(postList.get(position)));
    }

    @Override
    public int getItemCount() {
        if(postList != null){
            return postList.size();
        }
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final LinearLayout layout;
        private final TextView tvTitle;
        private final TextView tvDesc;
        private final TextView tvDate;

        private final ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagePost);
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

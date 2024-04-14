package com.thuanht.eatez.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ItemTrendingBinding;
import com.thuanht.eatez.interfaceEvent.MyClickItemListener;
import com.thuanht.eatez.model.Trending;

import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.MyViewHolder>{

    private List<Trending> trendings;
    private MyClickItemListener<Trending> listener;

    private Context context;

    public TrendingAdapter(List<Trending> trendings, MyClickItemListener<Trending> listener, Context context) {
        this.trendings = trendings;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTrendingBinding binding = ItemTrendingBinding.inflate(LayoutInflater.from(this.context), parent, false);;
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Trending t = trendings.get(position);
        holder.binding.tvTrending.setText(t.getTitle());
        holder.binding.imgTrending.setBackgroundResource(R.drawable.onboarding_img_3);
    }

    @Override
    public int getItemCount() {
        return trendings.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemTrendingBinding binding;
        public MyViewHolder(@NonNull ItemTrendingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

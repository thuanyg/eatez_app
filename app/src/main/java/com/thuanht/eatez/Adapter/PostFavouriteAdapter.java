package com.thuanht.eatez.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ItemPostFavouriteBinding;
import com.thuanht.eatez.model.Favourite;
import java.util.List;

public class PostFavouriteAdapter extends RecyclerView.Adapter<PostFavouriteAdapter.MyViewHolder>{
    private final List<Favourite> favouriteList;

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private final Context context;

    public PostFavouriteAdapter(List<Favourite> favouriteList, Context context) {
        this.favouriteList = favouriteList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostFavouriteBinding binding = ItemPostFavouriteBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Favourite fv = favouriteList.get(position);

        viewBinderHelper.bind(holder.binding.swipeRevealFavourite, fv.getFavouriteId());

        Glide.with(context)
                .load(fv.getThumbnailImage())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.binding.imageViewDishesFavourite);
        holder.binding.tvTitleDishFavourite.setText(fv.getTitle());
        holder.binding.tvLocationFavourite.setText(fv.getRestaurant().getResAddress());
        holder.binding.tvDateFavourite.setText(fv.getDate());
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final ItemPostFavouriteBinding binding;
        public MyViewHolder(@NonNull ItemPostFavouriteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

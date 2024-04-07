package com.thuanht.eatez.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.thuanht.eatez.databinding.ItemDishesFavouriteBinding;
import com.thuanht.eatez.model.Favourite;
import java.util.List;

public class DishesFavouriteAdapter extends RecyclerView.Adapter<DishesFavouriteAdapter.MyViewHolder>{

    private final List<Favourite> favouriteList;
    private final Context context;

    public DishesFavouriteAdapter(List<Favourite> favouriteList, Context context) {
        this.favouriteList = favouriteList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDishesFavouriteBinding binding = ItemDishesFavouriteBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Favourite fv = favouriteList.get(position);
//        holder.imageView.setImageResource(fv.);
        holder.binding.tvTitleDishFavourite.setText(fv.getTitle());
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final ItemDishesFavouriteBinding binding;
        public MyViewHolder(@NonNull ItemDishesFavouriteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

package com.thuanht.eatez.Adapter;

import android.content.Context;
import android.media.FaceDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.thuanht.eatez.R;
import com.thuanht.eatez.model.Favourite;

import java.util.List;

import kotlin.jvm.internal.Lambda;

public class DishesFavouriteAdapter extends RecyclerView.Adapter<DishesFavouriteAdapter.MyViewHolder>{

    private List<Favourite> favouriteList;
    private Context context;

    public DishesFavouriteAdapter(List<Favourite> favouriteList, Context context) {
        this.favouriteList = favouriteList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dishes_favourite, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Favourite fv = favouriteList.get(position);
//        holder.imageView.setImageResource(fv.);
        holder.tvTitle.setText(fv.getTitle());
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        RoundedImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleDishFavourite);
            imageView = itemView.findViewById(R.id.imageViewDishesFavourite);
        }
    }

}

package com.thuanht.eatez.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ItemCategoryBinding;
import com.thuanht.eatez.interfaceEvent.onClickItemListener;
import com.thuanht.eatez.model.Category;
import com.thuanht.eatez.model.Post;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private final List<Category> categoryList;
    private final Context context;

    private onClickItemListener<Category> listener;

    public CategoryAdapter(List<Category> categoryList, Context context, onClickItemListener<Category> listener) {
        this.categoryList = categoryList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding itemCategoryBinding = ItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(itemCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (categoryList != null && position < categoryList.size()) {
            Category category = categoryList.get(position);
            if (category != null) {
                String urlImage = category.getCimage();
                Glide.with(context)
                        .load(urlImage)
                        .placeholder(R.drawable.placeholder_image)
                        .into(holder.itemCategoryBinding.imageViewCategory);
                holder.itemCategoryBinding.tvCategoryName.setText(category.getCname());
            }
        }

        holder.itemCategoryBinding.imageViewCategory.setOnClickListener(v -> {
            listener.onClick(categoryList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final ItemCategoryBinding itemCategoryBinding;
        public MyViewHolder(@NonNull ItemCategoryBinding itemCategoryBinding) {
            super(itemCategoryBinding.getRoot());
            this.itemCategoryBinding = itemCategoryBinding;
        }
    }
}

package com.thuanht.eatez.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.thuanht.eatez.R;
import com.thuanht.eatez.interfaceEvent.MyClickItemListener;
import com.thuanht.eatez.model.SliderHome;

import java.util.List;

public class SliderHomeAdapter extends RecyclerView.Adapter<SliderHomeAdapter.SliderViewHolder> {

    private List<SliderHome> sliderHomeList;
    private Context context;
    private ViewPager2 viewPager;

    private MyClickItemListener<SliderHome> itemListener;

    public SliderHomeAdapter(Context context, List<SliderHome> sliderHomeList, ViewPager2 viewPager, MyClickItemListener<SliderHome> itemListener) {
        this.sliderHomeList = sliderHomeList;
        this.viewPager = viewPager;
        this.context = context;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider_home, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        if (!sliderHomeList.isEmpty() && sliderHomeList != null) {
            String urlImage = sliderHomeList.get(position).getImage();
            Glide.with(context)
                    .load(urlImage)
//                    .placeholder(R.drawable.)
                    .into(holder.imageView);
        }

        holder.imageView.setOnClickListener(v -> itemListener.onClick(sliderHomeList.get(position)));
    }

    @Override
    public int getItemCount() {
        if (!sliderHomeList.isEmpty() || sliderHomeList != null) {
            return sliderHomeList.size();
        }
        return 0;
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_slider_item);
        }
    }

}

package com.thuanht.eatez.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;
import com.thuanht.eatez.R;
import com.thuanht.eatez.model.SliderHome;

import java.util.List;

public class SliderHomeAdapter extends RecyclerView.Adapter<SliderHomeAdapter.SliderViewHolder>{

    private List<SliderHome> sliderHomeList;
    private ViewPager2 viewPager;

    public SliderHomeAdapter(List<SliderHome> sliderHomeList, ViewPager2 viewPager) {
        this.sliderHomeList = sliderHomeList;
        this.viewPager = viewPager;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider_home, parent, false);
        return new SliderViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(sliderHomeList.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderHomeList.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_slider_item);
        }

        void setImage(SliderHome sliderHome){
            imageView.setImageResource(sliderHome.getImage());
        }
    }
}

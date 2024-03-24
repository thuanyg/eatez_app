package com.thuanht.eatez.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.thuanht.eatez.R;


public class OnboardingViewPagerAdapter extends PagerAdapter {
    Context context;
    int slider_images[] = {
            R.drawable.onboarding_img_1,
            R.drawable.onboarding_img_2,
            R.drawable.onboarding_img_3
    };

    int slider_titles[] = {
            R.string.ob_title1,
            R.string.ob_title2,
            R.string.ob_title3
    };

    int slider_descs[] = {
            R.string.ob_desc1,
            R.string.ob_desc2,
            R.string.ob_desc3
    };
    public OnboardingViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return slider_titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_onboarding, container, false);

        ImageView sliderImage = view.findViewById(R.id.slider_image);
        TextView sliderTitle = view.findViewById(R.id.slider_title);
        TextView sliderDesc = view.findViewById(R.id.slider_desc);

        sliderImage.setImageResource(this.slider_images[position]);
        sliderTitle.setText(this.slider_titles[position]);
        sliderDesc.setText(this.slider_descs[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}

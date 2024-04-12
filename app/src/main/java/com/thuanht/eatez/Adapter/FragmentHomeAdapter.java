package com.thuanht.eatez.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.view.Fragment.FeatureFragment;
import com.thuanht.eatez.view.Fragment.LatestFragment;

import java.util.List;

public class FragmentHomeAdapter extends FragmentStateAdapter {

    private static final int TOTAL_FRAGMENT = 2;
    private FeatureFragment featureFragment;
    private LatestFragment latestFragment;

    public FragmentHomeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            featureFragment = new FeatureFragment();
            return featureFragment;
        }
        latestFragment = new LatestFragment();
        return latestFragment;
    }

    @Override
    public int getItemCount() {
        return TOTAL_FRAGMENT;
    }

    public FeatureFragment getFeatureFragment(){
        return featureFragment;
    }

    public LatestFragment getLatestFragment(){
        return latestFragment;
    }
}

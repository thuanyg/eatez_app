package com.thuanht.eatez.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.thuanht.eatez.view.Fragment.FavoriteFragment;
import com.thuanht.eatez.view.Fragment.HomeFragment;
import com.thuanht.eatez.view.Fragment.NotificationFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerHomeAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    public ViewPagerHomeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments.add(new HomeFragment());
        fragments.add(new FavoriteFragment());
        fragments.add(new NotificationFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}

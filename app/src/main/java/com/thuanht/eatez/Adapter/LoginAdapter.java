package com.thuanht.eatez.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.thuanht.eatez.view.Fragment.LoginTabFragment;
import com.thuanht.eatez.view.Fragment.SignUpTabFragment;

public class LoginAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTab;

    public LoginAdapter(@NonNull FragmentManager fm, Context context, int totalTab) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        this.totalTab = totalTab;
    }

    public Fragment getItem(int position){
        switch (position) {
            case 0:
                return new LoginTabFragment();
            case 1:
                return new SignUpTabFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTab;
    }
}

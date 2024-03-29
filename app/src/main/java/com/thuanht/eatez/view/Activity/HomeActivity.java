package com.thuanht.eatez.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.WindowManager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home_black));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_favorites));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_notification));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_setting));
        binding.bottomNavigation.setOnShowListener(model -> {
            Fragment fragment = null;
            switch (model.getId()) {
                case 1:
                    fragment = new HomeFragment();
                    break;
                case 2:
                    fragment = new FavoriteFragment();
                    break;
                case 3:
                    fragment = new NotificationFragment();
                    break;
                case 4:
                    fragment = new SettingFragment();
                    break;
            }
            loadFragment(fragment);
            return null;
        });
        binding.bottomNavigation.show(1, true);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(binding.frameLayout.getId(), fragment)
                .commit();
    }
}
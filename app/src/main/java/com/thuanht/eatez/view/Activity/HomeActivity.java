package com.thuanht.eatez.view.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivityHomeBinding;
import com.thuanht.eatez.view.Fragment.FavoriteFragment;
import com.thuanht.eatez.view.Fragment.HomeFragment;
import com.thuanht.eatez.view.Fragment.NotificationFragment;
import com.thuanht.eatez.view.Fragment.SettingFragment;

import java.util.ArrayDeque;
import java.util.Stack;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private int previousSelectedItemId = R.id.nav_home;

    ArrayDeque<Integer> deque = new ArrayDeque<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUI();
    }

    private void initUI() {
        HomeFragment homefragment = new HomeFragment();
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        NotificationFragment notificationFragment = new NotificationFragment();
        SettingFragment settingFragment = new SettingFragment();
        loadFragment(homefragment);
        binding.bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id != R.id.nav_setting) previousSelectedItemId = id;
            if (id == R.id.nav_home) {
                loadFragment(homefragment);
                return true;
            }
            if (id == R.id.nav_favourite) {
                loadFragment(favoriteFragment);
                return true;
            }
            if (id == R.id.nav_notification) {
                loadFragment(notificationFragment);
                return true;
            }
            if (id == R.id.nav_setting) {
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                binding.bottomNav.post(() -> {
                    binding.bottomNav.setSelectedItemId(previousSelectedItemId);
                });
                return true;
            }
            return false;
        });

    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(binding.frameLayout.getId(), fragment)
                .commit();
    }


}
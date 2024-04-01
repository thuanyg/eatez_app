package com.thuanht.eatez.view.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thuanht.eatez.Adapter.FragmentHomeAdapter;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivityHomeBinding;
import com.thuanht.eatez.databinding.FragmentFavoriteBinding;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.view.Fragment.FavoriteFragment;
import com.thuanht.eatez.view.Fragment.FeatureFragment;
import com.thuanht.eatez.view.Fragment.HomeFragment;
import com.thuanht.eatez.view.Fragment.NotificationFragment;

import java.util.ArrayDeque;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private int previousSelectedItemId = R.id.nav_home;

    HomeFragment homefragment = new HomeFragment();
    FavoriteFragment favoriteFragment = new FavoriteFragment();
    NotificationFragment notificationFragment = new NotificationFragment();

    ArrayDeque<Integer> deque = new ArrayDeque<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUI();
        eventHandler();
    }

    public void eventHandler(){
        binding.swipeRefreshHome.setOnRefreshListener(() -> {
            HomeFragment containingFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout);
            ViewPager2 viewPager = containingFragment.getView().findViewById(R.id.viewPager_Home);
            FragmentHomeAdapter homeAdapter = (FragmentHomeAdapter) viewPager.getAdapter();
            if (homeAdapter != null) {
                FeatureFragment featureFragment = homeAdapter.getFeatureFragment();
                if (featureFragment != null) {
                    // Sử dụng Executor để quản lý luồng
                    featureFragment.getShimmerFrameLayout().startShimmer();
                    featureFragment.getShimmerFrameLayout().setVisibility(View.VISIBLE);
                    featureFragment.getRecyclerView().setVisibility(View.INVISIBLE);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        List<Post> list = featureFragment.fetchData();
                        runOnUiThread(() -> {
                            binding.swipeRefreshHome.setRefreshing(false);
                            featureFragment.applyChanges(list);
                            featureFragment.getShimmerFrameLayout().stopShimmer();
                            featureFragment.getShimmerFrameLayout().setVisibility(View.GONE);
                            featureFragment.getRecyclerView().setVisibility(View.VISIBLE);
                        });
                    });

                }
            }
        });

    }

    private void initUI() {
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

//    @Override
//    public void onBackPressed() {
//            if(FavoriteFragment.current != null){
//                favoriteFragment.replaceTabSelected(FavoriteFragment.current, FavoriteFragment.selected);
//            }
//            super.onBackPressed();
//    }
}
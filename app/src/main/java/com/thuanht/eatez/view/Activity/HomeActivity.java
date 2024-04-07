package com.thuanht.eatez.view.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.thuanht.eatez.Adapter.FragmentHomeAdapter;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivityHomeBinding;
import com.thuanht.eatez.databinding.FragmentFavoriteBinding;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.permission.LocationPermission;
import com.thuanht.eatez.untils.NetworkUtils;
import com.thuanht.eatez.view.Fragment.FavoriteFragment;
import com.thuanht.eatez.view.Fragment.FeatureFragment;
import com.thuanht.eatez.view.Fragment.HomeFragment;
import com.thuanht.eatez.view.Fragment.NotificationFragment;

import java.util.ArrayDeque;
import java.util.ArrayList;
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
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
            return;
        }
        LocationPermission.getInstance(this).requestPermission(this);
    }

    public void eventHandler() {
//        binding.swipeRefreshHome.setOnRefreshListener(() -> {
//            Fragment containingFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
//
//            if(containingFragment instanceof HomeFragment && containingFragment != null){
//                ViewPager2 viewPager = containingFragment.getView().findViewById(R.id.viewPager_Home);
//                FragmentHomeAdapter homeAdapter = (FragmentHomeAdapter) viewPager.getAdapter();
//                if (homeAdapter != null) {
//                    FeatureFragment featureFragment = homeAdapter.getFeatureFragment();
//                    if (featureFragment != null) {
//                        // Sử dụng Executor để quản lý luồng
//                        featureFragment.getShimmerFrameLayout().startShimmer();
//                        featureFragment.getShimmerFrameLayout().setVisibility(View.VISIBLE);
//                        featureFragment.getRecyclerView().setVisibility(View.INVISIBLE);
//                        Executor executor = Executors.newSingleThreadExecutor();
//                        List<Post> newData = new ArrayList<>();
//                        executor.execute(() -> {
//                            try {
//                                Thread.sleep(2000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            featureFragment.callApiGetPosts(new FeatureFragment.ApiCallback() {
//                                @Override
//                                public void onDataLoaded(List<Post> posts) {
//                                    // Áp dụng các thay đổi giao diện
//                                    runOnUiThread(() -> {
//                                        featureFragment.applyChanges(posts);
//                                        binding.swipeRefreshHome.setRefreshing(false);
//                                        featureFragment.getShimmerFrameLayout().stopShimmer();
//                                        featureFragment.getShimmerFrameLayout().setVisibility(View.GONE);
//                                        featureFragment.getRecyclerView().setVisibility(View.VISIBLE);
//                                    });
//                                }
//
//                                @Override
//                                public void onFailure(String errorMessage) {
//                                    // Xử lý lỗi
//                                    runOnUiThread(() -> Toast.makeText(HomeActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show());
//                                }
//                            });
//                        });
//
//                    }
//                }
//            }
//
//
//
//        });

        binding.swipeRefreshHome.setOnRefreshListener(() -> {
            Fragment containingFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
            if (containingFragment instanceof HomeFragment && containingFragment != null) {
                ViewPager2 viewPager = containingFragment.getView().findViewById(R.id.viewPager_Home);
                FragmentHomeAdapter homeAdapter = (FragmentHomeAdapter) viewPager.getAdapter();

                //Get and start shimmer
                ShimmerFrameLayout shimmerFrameLayout = homeAdapter.getFeatureFragment().getView().findViewById(R.id.shimmerHome);
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                //Get recyclerView
                RecyclerView rcvFeatures = homeAdapter.getFeatureFragment().getView().findViewById(R.id.rcvFeatures);
                rcvFeatures.setVisibility(View.GONE);
                // Update data
                homeAdapter.getFeatureFragment().callApiGetPosts();
            }

            binding.swipeRefreshHome.setRefreshing(false);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LocationPermission.REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
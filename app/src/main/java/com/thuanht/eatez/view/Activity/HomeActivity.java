package com.thuanht.eatez.view.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.thuanht.eatez.Adapter.ViewPagerHomeAdapter;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivityHomeBinding;
import com.thuanht.eatez.permission.LocationPermission;
import com.thuanht.eatez.utils.NetworkUtils;
import com.thuanht.eatez.view.Fragment.FavoriteFragment;
import com.thuanht.eatez.view.Fragment.HomeFragment;
import com.thuanht.eatez.view.Fragment.NotificationFragment;

import java.util.List;
import java.util.Stack;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private Stack<Integer> fragmentStack = new Stack<>();
    private ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViewPager();
        initNavigation();
        eventHandler();
        LocationPermission.getInstance(this).requestPermission(this);
//        requestPermissions();
    }

    private void requestPermissions() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }

        };
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            TedPermission.create()
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.POST_NOTIFICATIONS)
                    .check();
        }

    }

    private void initViewPager() {
        viewPager = binding.viewPagerBottomNav;
        viewPager.setUserInputEnabled(false);
        ViewPagerHomeAdapter viewPagerHomeAdapter = new ViewPagerHomeAdapter(this);
        viewPager.setAdapter(viewPagerHomeAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.bottomNav.getMenu().getItem(position).setChecked(true);
            }
        });
    }
    private void initNavigation() {
        Context context = getApplicationContext();
        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    binding.viewPagerBottomNav.setCurrentItem(0, false);
//                    fragmentStack.add(0);
                    return true;
                } else if (itemId == R.id.nav_favourite) {
                    binding.viewPagerBottomNav.setCurrentItem(1, false);
//                    fragmentStack.add(1);
                    return true;
                } else if (itemId == R.id.nav_notification) {
                    binding.viewPagerBottomNav.setCurrentItem(2, false);
//                    fragmentStack.add(2);
                    return true;
                } else {
                    startActivity(new Intent(context, SettingActivity.class));
                    return false;
                }
            }
        });
    }

    private void switchToFragment(int position) {
        binding.viewPagerBottomNav.setCurrentItem(position);
        binding.bottomNav.setSelectedItemId(position);
    }

    public void eventHandler() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LocationPermission.REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
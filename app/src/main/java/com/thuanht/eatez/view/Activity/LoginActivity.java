package com.thuanht.eatez.view.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.thuanht.eatez.Adapter.LoginAdapter;
import com.thuanht.eatez.viewModel.LoginViewModel;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {
    public ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.tabLayout.addTab(binding.tabLayout.newTab());
        binding.tabLayout.addTab(binding.tabLayout.newTab());
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        binding.tabLayout.setTabIndicatorAnimationMode(TabLayout.INDICATOR_ANIMATION_MODE_LINEAR);
        binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
        LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this, binding.tabLayout.getTabCount());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.getTabAt(0).setText("Sign In");
        binding.tabLayout.getTabAt(1).setText("Sign Up");

        // Customize Google + Facebook

    }
}
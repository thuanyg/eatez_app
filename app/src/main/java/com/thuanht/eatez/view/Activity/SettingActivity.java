package com.thuanht.eatez.view.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarSetting);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.toolbarSetting.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
package com.thuanht.eatez.view.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.thuanht.eatez.LocalData.LocalDataManager;
import com.thuanht.eatez.LocalData.MySharedPreferences;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivitySettingBinding;
import com.thuanht.eatez.model.User;
import com.thuanht.eatez.viewModel.UserViewModel;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;
    private UserViewModel viewModel;
    private MySharedPreferences mySharedPreferences;

    private int userID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setSupportActionBar(binding.toolbarSetting);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initData();
        eventHandler();
    }

    private void eventHandler(){
        binding.toolbarSetting.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void initData(){
        User userLogin = LocalDataManager.getInstance().getUserLogin();
        if(userLogin != null){
            binding.tvEmailSetting.setText(userLogin.getEmail());
            binding.tvNameSetting.setText(userLogin.getFullName());
            Glide.with(this)
                    .load(userLogin.getAvatar_image())
                    .placeholder(R.drawable.onboarding_img_3)
                    .into(binding.avatarImgSetting);
        }
    }
}
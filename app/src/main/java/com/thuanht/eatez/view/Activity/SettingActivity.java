package com.thuanht.eatez.view.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.thuanht.eatez.LocalData.LocalDataManager;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivitySettingBinding;
import com.thuanht.eatez.firebase.FirebaseAuth.GoogleSignInManager;
import com.thuanht.eatez.model.User;
import com.thuanht.eatez.view.Dialog.DialogUtil;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarSetting);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initData();
        eventHandler();
    }

    private void eventHandler() {
        binding.toolbarSetting.setNavigationOnClickListener(v -> finish());

        binding.btnRecentlySetting.setOnClickListener(v -> startActivity(new Intent(this, RecentWatchedActivity.class)));

        binding.btnNotificationSetting.setOnClickListener(v -> {
            Toast.makeText(this, "This function is being developed.", Toast.LENGTH_SHORT).show();
        });

        binding.btnPrivacy.setOnClickListener(v -> {
            startActivity(new Intent(this, PrivacyActivity.class));
        });

        binding.btnDataStorage.setOnClickListener(v -> {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        });

        binding.btnLinkedDevice.setOnClickListener(v -> {
            Toast.makeText(this, "This function is being developed.", Toast.LENGTH_SHORT).show();
        });

        binding.btnChangeTheme.setOnClickListener(v -> {
            Toast.makeText(this, "This function is being developed.", Toast.LENGTH_SHORT).show();
        });

        binding.btnAboutUsSetting.setOnClickListener(v -> {
            startActivity(new Intent(this, AboutUsActivity.class));
        });

        binding.btnInviteFriend.setOnClickListener(v -> {
            Toast.makeText(this, "This function is being developed.", Toast.LENGTH_SHORT).show();
        });

        binding.btnSignOut.setOnClickListener(v -> {
            DialogUtil.showStandardDialog(this, "Log Out Comfirmation", "Are you sure logout this account?",
                    "Yes", "Cancel", new DialogUtil.DialogClickListener() {
                        @Override
                        public void onPositiveButtonClicked(Dialog dialog) {
                            LocalDataManager.getInstance().clearUserLogin();
                            GoogleSignInClient gsc = GoogleSignInManager.getInstance().getGoogleSignInClient();
                            gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    finish();
                                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                                }
                            });
                        }
                        @Override
                        public void onNegativeButtonClicked() {

                        }
                    });

        });

        binding.btnEditProfile.setOnClickListener(view -> {
            startActivity(new Intent(SettingActivity.this, EditProfileActivity.class));
        });
    }

    private void initData() {
        User userLogin = LocalDataManager.getInstance().getUserLogin();
        if (userLogin != null) {
            binding.tvEmailSetting.setText(userLogin.getEmail());
            binding.tvNameSetting.setText(userLogin.getFullName());
            Glide.with(this)
                    .load(userLogin.getAvatar_image())
                    .placeholder(R.drawable.onboarding_img_3)
                    .into(binding.avatarImgSetting);
        }
    }
}
package com.thuanht.eatez.view.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.thuanht.eatez.LocalData.LocalDataManager;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivityEditProfileBinding;
import com.thuanht.eatez.model.User;
import com.thuanht.eatez.permission.ImagePermission;
import com.thuanht.eatez.utils.RealPathUtil;
import com.thuanht.eatez.viewModel.EditProfileViewModel;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileActivity extends AppCompatActivity{
    private ActivityEditProfileBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    public static final int REQUEST_PERMISSION_CODE = 1;
    private Uri muri;
    private int userid;

    public Uri getMuri() {
        return muri;
    }

    private static final String TAG = EditProfileActivity.class.getName();
    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    Log.e(TAG, "hehe");
                    if(o.getResultCode() == Activity.RESULT_OK){
                        Intent data = o.getData();
                        if(data == null) return;
                        Uri uri = data.getData();
                        muri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            binding.avatarImgEdit.setImageBitmap(bitmap);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarEdit);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        initData();
        eventHandler();
    }

    private void eventHandler(){
        binding.toolbarEdit.setNavigationOnClickListener(v -> {
            finish();
        });

        binding.avatarImgEdit.setOnClickListener(v ->{
            chooseImage();
        });

        binding.btnEditSubmit.setOnClickListener(v ->{
                binding.progressEditProfile.setVisibility(View.VISIBLE);
                try{
                    if(muri!=null){
                        callApiUpdateProfile();
                        EditProfileViewModel.getUserDataLiveData().observe(this, user -> {
                            if(user != null){
                                LocalDataManager.getInstance().setUserLogin(user);
                                finish();
                                Intent intent = new Intent(this,SettingActivity.class);
                                startActivity(intent);
                            }
                            binding.progressEditProfile.setVisibility(View.GONE);
                        });
                    }
                    else{
                        callApiUpdateProfileNoImage();
                        EditProfileViewModel.getUserDataLiveData().observe(this, user -> {
                            if(user != null){
                                LocalDataManager.getInstance().setUserLogin(user);
                                finish();
                                Intent intent = new Intent(this,SettingActivity.class);
                                startActivity(intent);
                            }
                            binding.progressEditProfile.setVisibility(View.GONE);
                        });
                    }

                }catch (Exception e){

                }

        });
    }
    private void initData() {
        User userLogin = LocalDataManager.getInstance().getUserLogin();
        if (userLogin != null) {
            binding.edNameEdit.setText(userLogin.getFullName());
            Glide.with(this)
                    .load(userLogin.getAvatar_image())
                    .placeholder(R.drawable.onboarding_img_3)
                    .into(binding.avatarImgEdit);
        }
    }
    private void chooseImage() {
        if (ImagePermission.getInstance(this).requestPermission(this)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                chooseImage();
            }
        }
    }

    public void callApiUpdateProfile(){
        String fullname = binding.edNameEdit.getText().toString().trim();
        userid = LocalDataManager.getInstance().getUserLogin().getUserid();
        RequestBody requestBodyFullname = RequestBody.create(MediaType.parse("multipart/form-data"), fullname);

        String RealPath = RealPathUtil.getRealPath(this,muri);
        Log.e("mmazzzzz",RealPath);
        File file = new File(RealPath);
        RequestBody requestBodyAvta = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part mutilpartBodyAvata = MultipartBody.Part.createFormData("avatar_image",file.getName(),requestBodyAvta);
        Log.e("hehe", "userid: " + userid + ", fullname: " + requestBodyFullname + ", avatar: " + mutilpartBodyAvata);
        EditProfileViewModel.updateProfileAfterImageSelection(userid,requestBodyFullname,mutilpartBodyAvata);

    }public void callApiUpdateProfileNoImage(){
        String fullname = binding.edNameEdit.getText().toString().trim();
        userid = LocalDataManager.getInstance().getUserLogin().getUserid();
        RequestBody requestBodyFullname = RequestBody.create(MediaType.parse("multipart/form-data"), fullname);
        EditProfileViewModel.updateProfileUser(userid, requestBodyFullname, null);
    }
}

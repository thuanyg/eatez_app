package com.thuanht.eatez.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ImagePermission {
    private Context context;

    private static ImagePermission instance;
    public static final int REQUEST_PERMISSION_CODE = 1;

    private ImagePermission(Context context){this.context = context;}
    public static synchronized ImagePermission getInstance(Context context) {
        if (instance == null) {
            instance = new ImagePermission(context.getApplicationContext());
        }
        return instance;
    }
    public boolean requestPermission(Activity activity) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        if(ContextCompat.checkSelfPermission(context,Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            String[] permissions = {Manifest.permission.READ_MEDIA_IMAGES};
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_PERMISSION_CODE);
        }
        return false;
    }

}

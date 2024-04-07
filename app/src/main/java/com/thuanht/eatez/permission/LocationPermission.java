package com.thuanht.eatez.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LocationPermission {
    private static LocationPermission instance;
    public static final int REQUEST_PERMISSION_CODE = 1;
    private Context context;

    private LocationPermission(Context context) {
        this.context = context;
    }

    public static synchronized LocationPermission getInstance(Context context) {
        if (instance == null) {
            instance = new LocationPermission(context.getApplicationContext());
        }
        return instance;
    }

    public void requestPermission(Activity activity) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            // Quyền đã được cấp phép

        } else {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_PERMISSION_CODE);
        }
    }
}


package com.thuanht.eatez.permission;

import android.app.Activity;
import android.content.Context;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class NotificationPermission {
    private Activity activity;
    private static NotificationPermission instance;

    private NotificationPermission(Activity activity) {
        this.activity = activity;
    }
    public static synchronized NotificationPermission getInstance(Activity activity) {
        if (instance == null) {
            instance = new NotificationPermission(activity);
        }
        return instance;
    }
   
}

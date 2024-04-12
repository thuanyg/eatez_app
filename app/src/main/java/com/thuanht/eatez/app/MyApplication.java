package com.thuanht.eatez.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thuanht.eatez.LocalData.LocalDataManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LocalDataManager.getInstance().init(getApplicationContext());


        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
//                if (activity != null) {
//                    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR,
//                            WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR);
//                }
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }
}

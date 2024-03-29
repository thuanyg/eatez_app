package com.thuanht.eatez.App;

import android.app.Application;

import com.thuanht.eatez.LocalData.LocalDataManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LocalDataManager.getInstance().init(getApplicationContext());
    }
}

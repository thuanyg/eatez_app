package com.thuanht.eatez.LocalData;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class MySharedPreferences {
    private static final String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES";
    private static final String KEY_USER= "USERID";
    private static Context context;
    private Gson gson = new Gson();

    public MySharedPreferences(Context context) {
        this.context = context;
    }

    public <T> void setValue(String key, T value) {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        editor.putString(key, json);
        editor.apply();
    }

    public <T> T getValue(String key, Type type) {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(key, null);
        if (json != null) {
            Gson gson = new Gson();
            return gson.fromJson(json, type);
        }
        return null;
    }


    public void clearValue(String KEY) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY);
        editor.apply();
    }
}

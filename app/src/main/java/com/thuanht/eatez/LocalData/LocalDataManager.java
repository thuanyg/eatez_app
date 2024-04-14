package com.thuanht.eatez.LocalData;

import android.content.Context;

import com.google.common.reflect.TypeToken;
import com.thuanht.eatez.model.User;

import java.lang.reflect.Type;

public class LocalDataManager {
    private static final String PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL";
    private static final String LOGIN_USER_INFORMATION = "USERID";

    private MySharedPreferences mySharedPreferences;
    private static LocalDataManager unique;


    private LocalDataManager(){
    }

    public static void init(Context context) {
        if (unique == null) {
            unique = new LocalDataManager();
            unique.mySharedPreferences = new MySharedPreferences(context);
        }
    }
    public static LocalDataManager getInstance(){
        return unique;
    }

    public void setValueForFirstInstall(boolean isFirst){
        mySharedPreferences.setValue(PREF_FIRST_INSTALL,isFirst);
    }

    public Boolean checkIsFirstInstall(){
        Type type = new TypeToken<Boolean>(){}.getType();
        Boolean isFirstInstall = mySharedPreferences.getValue(PREF_FIRST_INSTALL, type);
        return isFirstInstall != null ? isFirstInstall : false;
    }

    public void setUserLogin(User user){
        mySharedPreferences.setValue(LOGIN_USER_INFORMATION, user);
    }

    public User getUserLogin(){
        Type type = new TypeToken<User>(){}.getType();
        User user = new User();
        user = mySharedPreferences.getValue(LOGIN_USER_INFORMATION, type);
        return user;
    }
}

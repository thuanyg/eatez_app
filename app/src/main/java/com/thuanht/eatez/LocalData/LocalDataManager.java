package com.thuanht.eatez.LocalData;

import android.content.Context;

public class LocalDataManager {
    private static final String PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL";
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
        mySharedPreferences.setBooleanValue(PREF_FIRST_INSTALL,isFirst);
    }

    public Boolean checkIsFirstInstall(){
        return mySharedPreferences.getBooleanValue(PREF_FIRST_INSTALL);
    }
}

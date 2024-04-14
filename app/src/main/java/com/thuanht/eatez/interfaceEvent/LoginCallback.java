package com.thuanht.eatez.interfaceEvent;

import android.content.Context;

import com.thuanht.eatez.model.User;

public interface LoginCallback {
    void onLoginSuccess(User user);
    void onLoginFailure(String errorMessage);
    Context getContext();
}

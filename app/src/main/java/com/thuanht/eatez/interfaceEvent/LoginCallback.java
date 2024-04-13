package com.thuanht.eatez.interfaceEvent;

import android.content.Context;

public interface LoginCallback {
    void onLoginSuccess(String data);
    void onLoginFailure(String errorMessage);
    Context getContext();
}

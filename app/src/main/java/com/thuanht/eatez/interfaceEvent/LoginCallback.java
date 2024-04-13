package com.thuanht.eatez.interfaceEvent;

import android.content.Context;

public interface LoginCallback {
    void onLoginSuccess();
    void onLoginFailure(String errorMessage);
    Context getContext();
}

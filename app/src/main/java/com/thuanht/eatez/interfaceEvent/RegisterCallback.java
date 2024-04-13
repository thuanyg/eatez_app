package com.thuanht.eatez.interfaceEvent;

public interface RegisterCallback {
    void onRegisterSuccess();
    void onRegisterFailure(String errorMessage);
}

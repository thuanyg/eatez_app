package com.thuanht.eatez.viewModel;

import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class LoginViewModel extends ViewModel {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private MutableLiveData<String> emailError = new MutableLiveData<>();
    private MutableLiveData<String> passwordError = new MutableLiveData<>();


    public boolean validateData(String email, String password){
        emailError.setValue(null);
        if(TextUtils.isEmpty(email)){
            emailError.setValue("Email cannot be empty");
            return false;
        }
        if(!email.matches(EMAIL_PATTERN)){
            emailError.setValue("Email is Invalid.");
            return false;
        }
        return true;
    }

    public void Login(String email, String password){
        // Login code

    }

    // Get Observable
    public MutableLiveData<String> getEmailError() {
        return emailError;
    }
    public MutableLiveData<String> getPasswordError() {
        return passwordError;
    }
}

package com.thuanht.eatez.viewModel;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thuanht.eatez.BR;

public class LoginViewModel extends BaseObservable{
    private String email, password;
    private Context context;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private MutableLiveData<String> emailError = new MutableLiveData<>();
    private MutableLiveData<String> passwordError = new MutableLiveData<>();
    public LoginViewModel() {
    }

    public LoginViewModel(Context context) {
        this.context = context;
    }

    public LiveData<String> getEmailError() {
        return emailError;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public boolean validateData(){
        String email;
        email = getEmail() == null ? "" : getEmail();
//        Toast.makeText(context, "email[" + getEmail() + "]", Toast.LENGTH_SHORT).show();
        emailError.setValue(null);
        boolean flag = true;
        if(TextUtils.isEmpty(email)){
            emailError.setValue("Email cannot be empty");
            flag = false;
        }
        if(!email.matches(EMAIL_PATTERN)){
            emailError.setValue("Email is Invalid.");
            flag = false;
        }
        return flag;
    }


    public void Login(){

    }
    private void showAlertDialog(int dialogLayout) {
        AlertDialog alertDialog;
        androidx.appcompat.app.AlertDialog.Builder builderDialog;

        builderDialog = new androidx.appcompat.app.AlertDialog.Builder(context);
        View layoutView = LayoutInflater.from(context).inflate(dialogLayout, null);
        //Get components in dialog layout

        builderDialog.setView(layoutView);
        alertDialog = builderDialog.create();
        alertDialog.show();
    }
}

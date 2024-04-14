package com.thuanht.eatez.viewModel;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Intent;
import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thuanht.eatez.interfaceEvent.LoginCallback;
import com.thuanht.eatez.jsonResponse.LoginResponse;
import com.thuanht.eatez.jsonResponse.PostResponse;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.retrofit.ApiService;
import com.thuanht.eatez.view.Activity.HomeActivity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class LoginViewModel extends ViewModel {
    private Disposable disposable;
    private MutableLiveData<Boolean> isLoginSuccess = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private LoginCallback loginCallback;

    public void setLoginCallback(LoginCallback callback) {
        this.loginCallback = callback;
    }
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
        // Login codeD
        ApiService.ApiService.Login(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(errors -> errors.flatMap(error -> {
                    if (error instanceof IOException) {
                        return Observable.timer(5, TimeUnit.SECONDS);
                    }
                    // Trả về một Observable không phải là IOException, không thử lại
                    return Observable.error(error);
                }))
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull LoginResponse loginResponse) {

                        if (loginResponse.isStatus()) {
                            isLoginSuccess.setValue(true);
                            loginCallback.onLoginSuccess(loginResponse.getUser());
                        }else{
                            isLoginSuccess.setValue(false);
                            loginCallback.onLoginFailure(loginResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });

    }

    // Get Observable
    public MutableLiveData<String> getEmailError() {
        return emailError;
    }
    public MutableLiveData<String> getPasswordError() {
        return passwordError;
    }

    public MutableLiveData<Boolean> getIsLoginSuccess() {
        return isLoginSuccess;
    }
}

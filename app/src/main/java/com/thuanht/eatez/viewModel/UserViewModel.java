package com.thuanht.eatez.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thuanht.eatez.jsonResponse.SignupResponse;
import com.thuanht.eatez.jsonResponse.UserResponse;
import com.thuanht.eatez.model.User;
import com.thuanht.eatez.retrofit.ApiService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserViewModel extends ViewModel {
    private Disposable disposable;
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<Boolean> status = new MutableLiveData<>();

    public void setToken(int userid, String token){
        ApiService.ApiService.setToken(userid, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SignupResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull SignupResponse signupResponse) {
                        if(signupResponse != null){
                            if(signupResponse.isStatus()){
                                status.setValue(true);
                            } else status.setValue(false);
                        } else status.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public Disposable getDisposable() {
        return disposable;
    }

    public MutableLiveData<Boolean> getStatus() {
        return status;
    }
}

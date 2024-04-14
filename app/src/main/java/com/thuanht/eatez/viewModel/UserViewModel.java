package com.thuanht.eatez.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

    public void fetchUser(int userid){
        ApiService.ApiService.getUser(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull UserResponse userResponse) {
                        if(userResponse  != null){
                            if(userResponse.isStatus() && userResponse.getData() != null){
                                user.setValue(userResponse.getData());
                                status.setValue(Boolean.TRUE);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        status.setValue(Boolean.FALSE);
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

    public MutableLiveData<Boolean> getStatus() {
        return status;
    }
}

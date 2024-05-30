package com.thuanht.eatez.viewModel;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thuanht.eatez.LocalData.LocalDataManager;
import com.thuanht.eatez.jsonResponse.UserResponse;
import com.thuanht.eatez.model.User;
import com.thuanht.eatez.retrofit.ApiService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileViewModel extends ViewModel {
    private static Disposable disposable;
    private static MutableLiveData<User> userDataLiveData = new MutableLiveData<>();
    private static MutableLiveData<String> fullnameError = new MutableLiveData<>();
    public MutableLiveData<String> getFullnameError() {return fullnameError;}

    public static boolean validateData(String fullname){
        fullnameError.setValue(null);
        if(TextUtils.isEmpty(fullname)){
            fullnameError.setValue("Fullname cannot be empty");
            return false;
        }
        return true;
    }

    public static MutableLiveData<User> getUserDataLiveData() {
        return userDataLiveData;
    }

    public static void updateProfileAfterImageSelection(int userid, RequestBody fullname, MultipartBody.Part avatarImage) {
        ApiService.ApiService.updateProfileUser(userid, fullname, avatarImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(errors -> errors.flatMap(error -> {
                    if (error instanceof IOException) {
                        return Observable.timer(5, TimeUnit.SECONDS);
                    }
                    return Observable.error(error);
                }))
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull UserResponse userResponse) {
                        if (userResponse.isStatus()) {
                            userDataLiveData.setValue(userResponse.getData());
                        } else {
                            Log.e(TAG, "Update failed");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // Handle error
                        Log.e(TAG, "Error updating profile: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });


    }
    public static void updateProfileUser(int userid, RequestBody fullname, MultipartBody.Part avatarImage) {
            ApiService.ApiService.updateProfileUser(userid, fullname, null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .retryWhen(errors -> errors.flatMap(error -> {
                        if (error instanceof IOException) {
                            return Observable.timer(5, TimeUnit.SECONDS);
                        }
                        return Observable.error(error);
                    }))
                    .subscribe(new Observer<UserResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onNext(@NonNull UserResponse userResponse) {
                            if (userResponse.isStatus()) {
                                userDataLiveData.setValue(userResponse.getData());
                            } else {
                                Log.e(TAG, "Update failed");
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            // Handle error
                            Log.e(TAG, "Error updating profile: " + e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            disposable.dispose();
                        }
                    });


        }



}

package com.thuanht.eatez.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thuanht.eatez.jsonResponse.StatusResponse;
import com.thuanht.eatez.retrofit.ApiService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SavePostViewModel extends ViewModel {
    private MutableLiveData<Boolean> isSaveSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> isUnSaveSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> isUserSavedBefore = new MutableLiveData<>();

    public void checkSaveBefore(int userid, int postid) {
        ApiService.ApiService.checkSave(userid, postid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StatusResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull StatusResponse statusResponse) {
                        if (!statusResponse.getStatus()) {
                            // Bai viet da duoc luu truoc do
                            isUserSavedBefore.setValue(true);
                        } else isUserSavedBefore.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void savePost(int userid, int postid) {
        ApiService.ApiService.savePost(userid, postid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StatusResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull StatusResponse statusResponse) {
                        if (statusResponse.getStatus()) isSaveSuccess.setValue(true);
                        else isSaveSuccess.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void unSavePost(int userid, int postid) {
        ApiService.ApiService.unSavePost(userid, postid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StatusResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull StatusResponse statusResponse) {
                        if (statusResponse.getStatus()) isUnSaveSuccess.setValue(true);
                        else isUnSaveSuccess.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public MutableLiveData<Boolean> getIsSaveSuccess() {
        return isSaveSuccess;
    }

    public MutableLiveData<Boolean> getIsUnSaveSuccess() {
        return isUnSaveSuccess;
    }

    public MutableLiveData<Boolean> getIsUserSavedBefore() {
        return isUserSavedBefore;
    }
}

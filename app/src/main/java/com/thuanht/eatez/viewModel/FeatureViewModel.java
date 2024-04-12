package com.thuanht.eatez.viewModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thuanht.eatez.jsonResponse.PostResponse;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.retrofit.ApiService;
import com.thuanht.eatez.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FeatureViewModel extends ViewModel {

    private Disposable disposable;
    private MutableLiveData<Boolean> isLastPageLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Post>> posts = new MutableLiveData<>();

    public void fetchFeaturePosts(Context context, int pageNumber) {
        ApiService.ApiService.getListPost(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(errors -> errors.flatMap(error -> {
                    if (error instanceof IOException) {
                        return Observable.timer(5, TimeUnit.SECONDS);
                    }
                    // Trả về một Observable không phải là IOException, không thử lại
                    return Observable.error(error);
                }))
                .subscribe(new Observer<PostResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull PostResponse postResponse) {
                        Log.d("TagDev", postResponse.getPagination().toString());
                        if (postResponse != null) {
                            if (postResponse.getStatus()) {
                                if (postResponse.getPagination().getCurrentPage() == postResponse.getPagination().getTotalPage()) {
                                    isLastPageLiveData.setValue(true);
                                }
                                posts.setValue(postResponse.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        if (e instanceof IOException) {
                            // Lỗi mất kết nối mạng
                            Toast.makeText(context, "Netword disconnected!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Lỗi không phải do mạng
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TagDev", "CAll API Success");
                    }
                });
    }

    public MutableLiveData<List<Post>> getPosts() {
        return posts;
    }

    public MutableLiveData<Boolean> getIsLastPageLiveData() {
        return isLastPageLiveData;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPageLiveData.setValue(isLastPage);
    }
}

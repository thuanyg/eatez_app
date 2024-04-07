package com.thuanht.eatez.viewModel;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.thuanht.eatez.jsonResponse.PostResponse;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.retrofit.ApiService;
import com.thuanht.eatez.untils.NetworkUtils;
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
    private MutableLiveData<List<Post>> posts = new MutableLiveData<>();

    public MutableLiveData<List<Post>> getPosts() {
        return posts;
    }

    public void fetchPosts(Context context){
        ApiService.ApiService.getListPost(1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(observable -> observable.flatMap((Function<? super Throwable, ? extends ObservableSource<?>>) throwable -> {
                    if (NetworkUtils.isNetworkAvailable(context.getApplicationContext())) {
                        // Nếu có mạng trở lại, thử lại kết nối
                        return Observable.just(true).delay(1, TimeUnit.SECONDS);
                    }
                    return Observable.error(throwable);
                }))
                .subscribe(new Observer<PostResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull PostResponse postResponse) {
                        posts.setValue(postResponse.getData());
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

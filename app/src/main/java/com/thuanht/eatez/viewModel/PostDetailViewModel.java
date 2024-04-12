package com.thuanht.eatez.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thuanht.eatez.jsonResponse.PostResponse;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.retrofit.ApiService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostDetailViewModel extends ViewModel {
    private Disposable disposable;
    private MutableLiveData<Post> post = new MutableLiveData<>();

    public void fetchPostDetail(int postid) {
        ApiService.ApiService.getDetailPost(postid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull PostResponse postResponse) {
                        if(postResponse != null){
                            post.setValue(postResponse.getData().get(0));
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

    public MutableLiveData<Post> getPost() {
        return post;
    }
}

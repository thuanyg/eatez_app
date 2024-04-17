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

public class PostTrendViewModel extends ViewModel {
    private MutableLiveData<List<Post>> posts = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLastPageLiveData = new MutableLiveData<>();

    public void fetchPostTrends(int dishID, int page){
        ApiService.ApiService.getPostTrending(dishID, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PostResponse postResponse) {
                        if (postResponse != null) {
                            if (postResponse.getStatus()) {
                                if (postResponse.getPagination().getCurrentPage() == postResponse.getPagination().getTotalPage()) {
                                    isLastPageLiveData.setValue(true);
                                }
                                posts.setValue(postResponse.getData());
                            } else {
                                posts.setValue(null);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public MutableLiveData<List<Post>> getPosts() {
        return posts;
    }

    public MutableLiveData<Boolean> getIsLastPageLiveData() {
        return isLastPageLiveData;
    }


}

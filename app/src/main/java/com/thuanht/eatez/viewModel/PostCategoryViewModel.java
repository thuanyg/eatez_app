package com.thuanht.eatez.viewModel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
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

public class PostCategoryViewModel extends ViewModel {
    private Disposable disposable;
    private MutableLiveData<List<Post>> posts = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLastPage = new MutableLiveData<>();
    public void fetchPostOfCategory(Context context, int categoryID, int page){
        ApiService.ApiService.getListPostOfCategory(categoryID, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull PostResponse postResponse) {
                        if (postResponse != null) {
                            if(postResponse.getStatus()){
                                if(postResponse.getPagination().getCurrentPage() == postResponse.getPagination().getTotalPage()){
                                    isLastPage.setValue(true);
                                }
                                posts.setValue(postResponse.getData());
                            } else {
                                if(postResponse.getData() == null){
//                                    Toast.makeText(context, "K co data", Toast.LENGTH_SHORT).show();
                                }
                                posts.setValue(null);
                            }
                        }
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

    public LiveData<List<Post>> getPosts() {
        return posts;
    }

    public LiveData<Boolean> getIsLastPage() {
        return isLastPage;
    }
}

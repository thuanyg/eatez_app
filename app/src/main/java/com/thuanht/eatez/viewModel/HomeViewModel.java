package com.thuanht.eatez.viewModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thuanht.eatez.jsonResponse.CategoryResponse;
import com.thuanht.eatez.jsonResponse.PostResponse;
import com.thuanht.eatez.jsonResponse.SliderResponse;
import com.thuanht.eatez.model.Category;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.model.SliderHome;
import com.thuanht.eatez.retrofit.ApiService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private Disposable disposable;
    private MutableLiveData<List<SliderHome>> sliderList = new MutableLiveData<>();
    private MutableLiveData<List<Category>> categoryList = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLastPageLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Post>> posts = new MutableLiveData<>();


    public void fetchFeaturePosts(Context context, int pageNumber) {
        ApiService.ApiService.getListPost(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(errors -> errors.zipWith(Observable.range(1, 5), (error, retryCount) -> {
                    if (error instanceof IOException && retryCount < 5) {
                        return retryCount;
                    }
                    throw Exceptions    .propagate(error);
                }).flatMap(retryCount -> {
                    if (retryCount == 5) {
                        return Observable.error(new Throwable("Retry limit reached"));
                    } else {
                        return Observable.timer(5, TimeUnit.SECONDS);
                    }
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


    public void fetchSliderImages() {
        ApiService.ApiService.getSliders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(errors -> errors.flatMap(error -> {
                    if (error instanceof IOException) {
                        return Observable.timer(5, TimeUnit.SECONDS);
                    }
                    // Trả về một Observable không phải là IOException, không thử lại
                    return Observable.error(error);
                }))
                .subscribe(new Observer<SliderResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull SliderResponse sliderResponse) {
                        sliderList.setValue(sliderResponse.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void fetchCategories() {
        ApiService.ApiService.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(errors -> errors.flatMap(error -> {
                    if (error instanceof IOException) {
                        return Observable.timer(5, TimeUnit.SECONDS);
                    }
                    // Trả về một Observable không phải là IOException, không thử lại
                    return Observable.error(error);
                }))
                .subscribe(new Observer<CategoryResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull CategoryResponse categoryResponse) {
                        categoryList.setValue(categoryResponse.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public MutableLiveData<List<SliderHome>> getSliderList() {
        return sliderList;
    }

    public MutableLiveData<List<Category>> getCategoryList() {
        return categoryList;
    }

    public MutableLiveData<Boolean> getIsLastPageLiveData() {
        return isLastPageLiveData;
    }

    public MutableLiveData<List<Post>> getPosts() {
        return posts;
    }
    public void setIsLastPage(boolean isLastPage) {
        this.isLastPageLiveData.setValue(isLastPage);
    }
}

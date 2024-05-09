package com.thuanht.eatez.viewModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thuanht.eatez.jsonResponse.CategoryResponse;
import com.thuanht.eatez.jsonResponse.PostResponse;
import com.thuanht.eatez.jsonResponse.SliderResponse;
import com.thuanht.eatez.jsonResponse.TrendingResponse;
import com.thuanht.eatez.model.Category;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.model.SliderHome;
import com.thuanht.eatez.model.Trending;
import com.thuanht.eatez.retrofit.ApiService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private Disposable disposable_postHome, disposable_category, disposable_slider, disposable_trending;
    private MutableLiveData<List<SliderHome>> sliderList = new MutableLiveData<>();
    private MutableLiveData<List<Category>> categoryList = new MutableLiveData<>();
    private MutableLiveData<List<Trending>> trends = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLastPageLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Post>> posts = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNetworkDisconnect = new MutableLiveData<>();
    int retryCount = 0;

    public void fetchFeaturePosts(Context context, int pageNumber) {
        isNetworkDisconnect.setValue(false);
        ApiService.ApiService.getListPost(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(errors -> errors.flatMap(error -> {
                    if (error instanceof IOException && retryCount < 2) {
                        retryCount++;
                        return Observable.timer(5, TimeUnit.SECONDS);
                    }
                    return Observable.error(error);
                }))
                .subscribe(new Observer<PostResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        disposable_postHome = d;
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
                        if (retryCount >= 2) {
                            isNetworkDisconnect.setValue(true);
                            disposable_postHome.dispose();
                        }
                    }

                    @Override
                    public void onComplete() {
                        disposable_postHome.dispose();
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
                        disposable_slider = d;
                    }

                    @Override
                    public void onNext(@NonNull SliderResponse sliderResponse) {
                        sliderList.setValue(sliderResponse.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        disposable_slider.dispose();
                    }

                    @Override
                    public void onComplete() {
                        disposable_slider.dispose();
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
                        disposable_category = d;
                    }

                    @Override
                    public void onNext(@NonNull CategoryResponse categoryResponse) {
                        categoryList.setValue(categoryResponse.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        disposable_category.dispose();
                    }

                    @Override
                    public void onComplete() {
                        disposable_category.dispose();
                    }
                });
    }

    public void fetchTrending() {
        ApiService.ApiService.getTrendingHome()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TrendingResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable_trending = d;
                    }

                    @Override
                    public void onNext(@NonNull TrendingResponse trendingResponse) {
                        if (trendingResponse != null) {
                            if (trendingResponse.getStatus()) {
                                trends.setValue(trendingResponse.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        disposable_trending.dispose();
                    }

                    @Override
                    public void onComplete() {
                        disposable_trending.dispose();
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

    public MutableLiveData<List<Trending>> getTrends() {
        return trends;
    }

    public MutableLiveData<Boolean> getIsNetworkDisconnect() {
        return isNetworkDisconnect;
    }
}

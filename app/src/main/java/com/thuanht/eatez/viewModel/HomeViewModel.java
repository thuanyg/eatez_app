package com.thuanht.eatez.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thuanht.eatez.jsonResponse.CategoryResponse;
import com.thuanht.eatez.jsonResponse.SliderResponse;
import com.thuanht.eatez.model.Category;
import com.thuanht.eatez.model.SliderHome;
import com.thuanht.eatez.retrofit.ApiService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private Disposable disposable;
    private MutableLiveData<List<SliderHome>> sliderList = new MutableLiveData<>();
    private MutableLiveData<List<Category>> categoryList = new MutableLiveData<>();

    public LiveData<List<SliderHome>> getSliderList() {
        return sliderList;
    }

    public LiveData<List<Category>> getCategoryList() {
        return categoryList;
    }

    public void fetchSliderImages() {
        ApiService.ApiService.getSliders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
}

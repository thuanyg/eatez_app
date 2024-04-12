package com.thuanht.eatez.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thuanht.eatez.jsonResponse.FavouriteResponse;
import com.thuanht.eatez.model.Favourite;
import com.thuanht.eatez.retrofit.ApiService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouriteViewModel extends ViewModel {
    private Disposable disposable;
    private MutableLiveData<List<Favourite>> favourites = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLastPage = new MutableLiveData<>();

    public void fetchFavouritePost(int userid, int pageNumber){
        ApiService.ApiService.getFavouritePost(userid, pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FavouriteResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull FavouriteResponse favouriteResponse) {
                        if(favouriteResponse != null){
                            if(favouriteResponse.getPagination().getCurrentPage() == favouriteResponse.getPagination().getTotalPage()){
                                isLastPage.setValue(true);
                            }
                            if(favouriteResponse.getData() != null){
                                favourites.setValue(favouriteResponse.getData());
                            }
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
    public LiveData<List<Favourite>> getFavourites() {
        return favourites;
    }

    public LiveData<Boolean> getIsLastPage() {
        return isLastPage;
    }


}

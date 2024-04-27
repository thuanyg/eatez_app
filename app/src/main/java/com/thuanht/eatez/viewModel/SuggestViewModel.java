package com.thuanht.eatez.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thuanht.eatez.jsonResponse.SuggestResponse;
import com.thuanht.eatez.model.Suggestion;
import com.thuanht.eatez.retrofit.ApiService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SuggestViewModel extends ViewModel {
    private Disposable disposable;
    private MutableLiveData<List<Suggestion>> listSuggestions = new MutableLiveData<>();

    public void fetchSuggestionValue(){
        ApiService.ApiService.getSuggestionValue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SuggestResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull SuggestResponse suggestResponse) {
                        if(suggestResponse != null){
                            if(suggestResponse.getStatus()){
                                listSuggestions.setValue(suggestResponse.getData());
                            } else listSuggestions.setValue(null);
                        } else listSuggestions.setValue(null);
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

    public MutableLiveData<List<Suggestion>> getListSuggestions() {
        return listSuggestions;
    }
}

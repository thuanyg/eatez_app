package com.thuanht.eatez.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.thuanht.eatez.model.Post;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final SavedStateHandle savedStateHandle;
    private MutableLiveData<List<Post>> dataList = new MutableLiveData<>();

    public HomeViewModel(SavedStateHandle savedStateHandle) {
        this.savedStateHandle = savedStateHandle;
    }

    public void setDataList(List<Post> data) {
        dataList.setValue(data);
    }

    public LiveData<List<Post>> getDataList() {
        return dataList;
    }
}

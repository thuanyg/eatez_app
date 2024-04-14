package com.thuanht.eatez.pagination;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;

public abstract class PaginationOnScrollListener implements NestedScrollView.OnScrollChangeListener {
    private NestedScrollView nestedScrollView;

    public PaginationOnScrollListener(NestedScrollView nestedScrollView) {
        this.nestedScrollView = nestedScrollView;
    }


    @Override
    public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
            if(isLastPage() || isLoading()) return;
            loadMoreItem();
        }
    }

    public abstract void loadMoreItem();
    public abstract boolean isLoading();
    public abstract boolean isLastPage();
}


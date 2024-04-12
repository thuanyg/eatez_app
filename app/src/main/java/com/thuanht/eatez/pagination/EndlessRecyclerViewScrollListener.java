package com.thuanht.eatez.pagination;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    // Số lượng mục phải được tải thêm khi người dùng cuộn đến cuối RecyclerView
    private int visibleThreshold = 10;
    // Index của trang cuối cùng đã được tải
    private int currentPage = 0;
    // Tổng số mục trên RecyclerView trước khi tải thêm
    private int previousTotalItemCount = 0;
    // Trạng thái tải dữ liệu
    private boolean loading = true;

    private RecyclerView.LayoutManager layoutManager;

    public EndlessRecyclerViewScrollListener(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int visibleThreshold = 2 * layoutManager.getSpanCount(); // Số cột của GridLayoutManager

        if (!loading && totalItemCount <= (lastVisibleItemPosition + visibleThreshold)) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount, recyclerView);
            loading = true;
        }
    }


    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
}


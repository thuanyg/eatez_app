package com.thuanht.eatez.view.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.thuanht.eatez.Adapter.PostFavouriteAdapter;
import com.thuanht.eatez.LocalData.LocalDataManager;
import com.thuanht.eatez.databinding.FragmentFavoriteBinding;
import com.thuanht.eatez.model.Favourite;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.view.Activity.PostDetailActivity;
import com.thuanht.eatez.view.Dialog.DialogUtil;
import com.thuanht.eatez.viewModel.FavouriteViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private FavouriteViewModel viewModel;
    private PostFavouriteAdapter adapter;
    private List<Favourite> favouriteList = new ArrayList<>();
    private int currentPage = 1;
    private boolean isLoading, isLastPage = false;
    private int userid = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(FavouriteViewModel.class);
        userid = LocalDataManager.getInstance().getUserLogin().getUserid();
        initUI();
        initData();
        eventHandler();
        return binding.getRoot();
    }

    private void eventHandler() {
//        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                int position = viewHolder.getAdapterPosition();
//                f_temp = favouriteList.get(position);
//                favouriteList.remove(position);
//                adapter.notifyItemRemoved(position);
//
//                Snackbar.make(binding.rcvDishesFavourite, "Xóa thành công", Snackbar.LENGTH_LONG).setAction("Undo", v -> {
//                    favouriteList.add(f_temp);
//                    adapter.notifyItemInserted(position);
//                }).show();
//            }
//        };
//
//        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.rcvDishesFavourite);

        binding.nestedScrollViewFav.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                LoadMoreData();
            }
        });

        binding.swipeRefreshFavourite.setOnRefreshListener(() -> {
            favouriteList.clear();
            currentPage = 1;
            viewModel.fetchFavouritePost(userid, 1);
            binding.swipeRefreshFavourite.setRefreshing(false);
        });
    }

    private void LoadMoreData() {
        if(this.isLastPage) {
            binding.progressLoadMoreFavourite.setVisibility(View.GONE);
            return;
        };
        binding.progressLoadMoreFavourite.setVisibility(View.VISIBLE);
        if(!isLoading){
            currentPage++;
            viewModel.fetchFavouritePost(userid, currentPage);
        }
    }

    private void initUI() {
        adapter = new PostFavouriteAdapter(favouriteList, requireContext(),
                // Callback go to detail post
                favourite -> {
                    if (favourite != null) {
                        goToPostDetailActivity(Integer.parseInt(favourite.getPostId()));
                    }
                },
                // Delete favourite item
                favourite -> {
                    DialogUtil.showStandardDialog(requireContext(), "Delete comfirmation", "Are you sure delete it?",
                            "Yes", "Cancel", new DialogUtil.DialogClickListener() {
                                @Override
                                public void onPositiveButtonClicked(Dialog dialog) {
                                    int position = favouriteList.indexOf(favourite);
                                    favouriteList.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    dialog.dismiss();
                                    adapter.closeSwipeReveal();
                                    Snackbar.make(binding.rcvDishesFavourite, "Xóa thành công", Snackbar.LENGTH_LONG).setAction("Undo", v -> {
                                        favouriteList.add(position, favourite);
                                        adapter.notifyItemInserted(position);
                                    }).show();
                                }

                                @Override
                                public void onNegativeButtonClicked() {

                                }
                            });
                });
        binding.rcvDishesFavourite.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));
        binding.rcvDishesFavourite.setAdapter(adapter);
    }

    public void initData() {
        isLoading = true;
        viewModel.getIsLastPage().observe(requireActivity(), aBoolean -> {
            this.isLastPage = aBoolean;
        });
        viewModel.getFavourites().observe(requireActivity(), new Observer<List<Favourite>>() {
            @Override
            public void onChanged(List<Favourite> favourites) {
                binding.progressLoadMoreFavourite.setVisibility(View.VISIBLE);
                if (favourites == null) {
                    binding.tvNoPostSaved.setVisibility(View.VISIBLE);
                    return;
                }
                if (!favourites.isEmpty()) {
                    if (favouriteList.isEmpty()) {
                        favouriteList.addAll(favourites);
                        adapter.notifyDataSetChanged();
                    } else {
                        int startInsertedIndex = favouriteList.size();
                        favouriteList.addAll(favourites);
                        adapter.notifyItemRangeInserted(startInsertedIndex, favouriteList.size());
                    }
                }
                isLoading = false;
                binding.progressLoadMoreFavourite.setVisibility(View.GONE);
            }
        });
        viewModel.fetchFavouritePost(userid, 1);
    }


    private void goToPostDetailActivity(int postid) {
        Intent intent = new Intent(requireContext(), PostDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("postid", postid);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
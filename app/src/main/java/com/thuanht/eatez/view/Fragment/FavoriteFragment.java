package com.thuanht.eatez.view.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ActionMode;
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
import com.thuanht.eatez.view.Activity.HomeActivity;
import com.thuanht.eatez.view.Activity.PostDetailActivity;
import com.thuanht.eatez.view.Dialog.DialogUtil;
import com.thuanht.eatez.viewModel.FavouriteViewModel;
import com.thuanht.eatez.viewModel.SavePostViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private FavouriteViewModel viewModel;
    private SavePostViewModel savePostViewModel;
    private PostFavouriteAdapter adapter;
    private List<Favourite> favouriteList = new ArrayList<>();
    private int currentPage = 1;
    private boolean isLoading, isLastPage = false;
    private int userid = 1;
    // Define for multi selection
    private ActionMode actionMode;
    private boolean isMultiSelect = false;
    //i created List of int type to store id of data, you can create custom class type data according to your need.
    private List<Integer> selectedIds = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(FavouriteViewModel.class);
        savePostViewModel = new ViewModelProvider(requireActivity()).get(SavePostViewModel.class);
        userid = LocalDataManager.getInstance().getUserLogin().getUserid();
        initUI();
        initData();
        eventHandler();
        return binding.getRoot();
    }

    private void eventHandler() {
        binding.nestedScrollViewFav.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                LoadMoreData();
            }
        });

        binding.swipeRefreshFavourite.setOnRefreshListener(() -> {
            refreshData();
        });
    }

    private void refreshData(){
        binding.progressRefreshFav.setVisibility(View.VISIBLE);
        favouriteList.clear();
        currentPage = 1;
        viewModel.fetchFavouritePost(userid, 1);
        binding.swipeRefreshFavourite.setRefreshing(false);
    }

    private void LoadMoreData() {
        if (this.isLastPage) {
            binding.progressLoadMoreFavourite.setVisibility(View.GONE);
            return;
        }
        ;
        binding.progressLoadMoreFavourite.setVisibility(View.VISIBLE);
        if (!isLoading) {
            currentPage++;
            viewModel.fetchFavouritePost(userid, currentPage);
        }
    }

    private void initUI() {
        adapter = new PostFavouriteAdapter(favouriteList, requireContext(),
                // Callback go to detail post
                new PostFavouriteAdapter.OnclickItemListener() {
                    @Override
                    public void onClick(Favourite favourite) {
                        if (favourite != null) {
                            goToPostDetailActivity(Integer.parseInt(favourite.getPostId()));
                        }
                    }

                    @Override
                    public void longClick(Favourite favourite) {

                    }
                },
                // Delete favourite item
                favourite -> {
                    int position = favouriteList.indexOf(favourite);
                    favouriteList.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.closeSwipeReveal();
                    // Remove from DB
                    savePostViewModel.unSavePost(userid, Integer.parseInt(favourite.getPostId()));
                    adapter.notifyItemRemoved(position);
                    Snackbar.make(binding.rcvDishesFavourite, "Xóa thành công", Snackbar.LENGTH_LONG).setAction("Undo", v -> {
                        favouriteList.add(position, favourite);
                        savePostViewModel.savePost(userid, Integer.parseInt(favourite.getPostId()));
                        adapter.notifyItemInserted(position);
                    }).show();
                });
        binding.rcvDishesFavourite.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));
        binding.rcvDishesFavourite.setAdapter(adapter);

        // OB
        savePostViewModel.getIsUnSaveSuccess().observe(requireActivity(), aBoolean -> {
            if(aBoolean){
                Toast.makeText(requireContext(), "Removed", Toast.LENGTH_SHORT).show();
            }
        });
        savePostViewModel.getIsSaveSuccess().observe(requireActivity(), aBoolean -> {
            if(aBoolean){
                Toast.makeText(requireContext(), "Restored", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnTryAgain.setOnClickListener(v -> {
            binding.layoutDisconnect.setVisibility(View.GONE);
            binding.swipeRefreshFavourite.setVisibility(View.VISIBLE);
            refreshData();
        });
    }

    public void initData() {
        isLoading = true;
        viewModel.getIsLastPage().observe(requireActivity(), aBoolean -> {
            this.isLastPage = aBoolean;
        });
        viewModel.getIsNetworkDisconnect().observe(getViewLifecycleOwner(), isNetworkDisconnet -> {
            if(isNetworkDisconnet){
                binding.swipeRefreshFavourite.setVisibility(View.GONE);
                binding.layoutDisconnect.setVisibility(View.VISIBLE);
                binding.progressLoadMoreFavourite.setVisibility(View.GONE);
            }
        });
        viewModel.getFavourites().observe(requireActivity(), new Observer<List<Favourite>>() {
            @Override
            public void onChanged(List<Favourite> favourites) {
                binding.progressLoadMoreFavourite.setVisibility(View.VISIBLE);
                if (favourites == null) {
                    binding.tvNoPostSaved.setVisibility(View.VISIBLE);
                    binding.progressLoadMoreFavourite.setVisibility(View.GONE);
                    binding.progressRefreshFav.setVisibility(View.GONE);
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
                binding.tvNoPostSaved.setVisibility(View.GONE);
                binding.progressLoadMoreFavourite.setVisibility(View.GONE);
                binding.progressRefreshFav.setVisibility(View.GONE);
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
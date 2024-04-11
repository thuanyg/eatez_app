package com.thuanht.eatez.view.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.thuanht.eatez.Adapter.PostFavouriteAdapter;
import com.thuanht.eatez.databinding.FragmentFavoriteBinding;
import com.thuanht.eatez.model.Favourite;
import com.thuanht.eatez.viewModel.FavouriteViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private FavouriteViewModel viewModel;
    private PostFavouriteAdapter adapter;
    private List<Favourite> favouriteList = new ArrayList<>();
    private Favourite f_temp = new Favourite();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(FavouriteViewModel.class);
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


    }

    private void initUI() {
        adapter = new PostFavouriteAdapter(favouriteList, requireContext());
        binding.rcvDishesFavourite.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));
        binding.rcvDishesFavourite.setAdapter(adapter);
    }
    public void initData() {
        binding.progressLoadFavourite.setVisibility(View.VISIBLE);
        viewModel.getFavourites().observe(requireActivity(), new Observer<List<Favourite>>() {
            @Override
            public void onChanged(List<Favourite> favourites) {
                if(favourites == null){
                    binding.tvNoPostSaved.setVisibility(View.VISIBLE);
                    binding.progressLoadFavourite.setVisibility(View.GONE);
                    return;
                }
                if(!favourites.isEmpty()){
                    if(favouriteList.isEmpty()){
                        favouriteList.addAll(favourites);
                        adapter.notifyDataSetChanged();
                    } else {
                        int startInsertedIndex = favouriteList.size();
                        favouriteList.addAll(favourites);
                        adapter.notifyItemRangeInserted(startInsertedIndex, favouriteList.size());
                    }
                }
                binding.progressLoadFavourite.setVisibility(View.GONE);
            }
        });
        viewModel.fetchFavouritePost(1, 1);
    }

}
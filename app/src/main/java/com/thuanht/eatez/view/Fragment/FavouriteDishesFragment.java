package com.thuanht.eatez.view.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thuanht.eatez.Adapter.DishesFavouriteAdapter;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.FragmentFavouriteDishesBinding;
import com.thuanht.eatez.model.Favourite;

import java.util.ArrayList;
import java.util.List;

public class FavouriteDishesFragment extends Fragment {
    private FragmentFavouriteDishesBinding binding;
    private DishesFavouriteAdapter adapter;

    private List<Favourite> favouriteList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavouriteDishesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favouriteList = new ArrayList<>();
        favouriteList.add(new Favourite("Hamburger"));
        favouriteList.add(new Favourite("Bún bò"));
        favouriteList.add(new Favourite("Cơm rang"));
        favouriteList.add(new Favourite("Bánh mì"));
        favouriteList.add(new Favourite("Xôi phú thượng"));
        favouriteList.add(new Favourite("Hamburger"));
        favouriteList.add(new Favourite("Bún bò"));
        favouriteList.add(new Favourite("Cơm rang"));
        favouriteList.add(new Favourite("Bánh mì"));
        favouriteList.add(new Favourite("Xôi phú thượng"));
        favouriteList.add(new Favourite("Hamburger"));
        favouriteList.add(new Favourite("Bún bò"));
        favouriteList.add(new Favourite("Cơm rang"));
        favouriteList.add(new Favourite("Bánh mì"));
        favouriteList.add(new Favourite("Xôi phú thượng"));
        favouriteList.add(new Favourite("Hamburger"));
        favouriteList.add(new Favourite("Bún bò"));
        favouriteList.add(new Favourite("Cơm rang"));
        favouriteList.add(new Favourite("Bánh mì"));
        favouriteList.add(new Favourite("Xôi phú thượng"));
        adapter = new DishesFavouriteAdapter(favouriteList, requireContext());
        binding.rcvDishesFavourite.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.rcvDishesFavourite.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.rcvDishesFavourite.setAdapter(adapter);
    }
}
package com.thuanht.eatez.view.Fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.FragmentFavoriteBinding;

public class FavoriteFragment extends Fragment implements View.OnClickListener{

    private FragmentFavoriteBinding bd;
    ColorStateList def;
    TextView dishes_tab;
    TextView res_tab;
    TextView select;

    public static TextView current = null, selected = null;

    Fragment fragmentDishes, fragmentRestaurant;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bd = FragmentFavoriteBinding.inflate(inflater, container, false);
        return bd.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentDishes = new FavouriteDishesFragment();
        fragmentRestaurant = new FavouriteRestaurantFragment();
        loadFragment(fragmentDishes);
        dishes_tab = bd.getRoot().findViewById(R.id.dishes_tab);
        res_tab = bd.getRoot().findViewById(R.id.restaurant_tab);
        select = bd.getRoot().findViewById(R.id.select);
        dishes_tab.setOnClickListener(this);
        res_tab.setOnClickListener(this);
        def = res_tab.getTextColors();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dishes_tab){
            replaceTabSelected(res_tab, dishes_tab);
            // Dishes Tab Clicked
            loadFragment(fragmentDishes);
        } else if (view.getId() == R.id.restaurant_tab){
            replaceTabSelected(dishes_tab, res_tab);
            loadFragment(fragmentRestaurant);
        }
    }

    private void loadFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(bd.frameLayoutFavourite.getId(), fragment, "FAVORITE_FRAGMENT_TAG")
//                .addToBackStack(null)
                .commit();
    }

    public void replaceTabSelected(TextView current, TextView tabSelected){
        if(current == res_tab) select.animate().x(0).setDuration(100);
        else {
            int size = res_tab.getWidth();
            select.animate().x(size).setDuration(100);
        }
        tabSelected.setTextColor(Color.WHITE);
        current.setTextColor(def);
        this.current = tabSelected;
        this.selected = current;
    }
}
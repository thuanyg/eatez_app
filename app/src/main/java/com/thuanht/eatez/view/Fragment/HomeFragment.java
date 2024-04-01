package com.thuanht.eatez.view.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.thuanht.eatez.Adapter.CategoryAdapter;
import com.thuanht.eatez.Adapter.FragmentHomeAdapter;
import com.thuanht.eatez.Adapter.SliderHomeAdapter;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.FragmentHomeBinding;
import com.thuanht.eatez.model.Category;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.model.SliderHome;
import com.thuanht.eatez.view.Activity.SearchActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    List<SliderHome> sliders;
    private FragmentHomeBinding binding;
    private ViewPager2 viewPager;
    FragmentHomeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initSlider();
        initCategory();
        initTabLayout(binding.tabLayoutHome);
//        initTabLayout(binding.tabLayoutHomeSticky);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        eventHandler();
    }





    private void eventHandler() {
        binding.btnToSearch.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        binding.nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // Lấy vị trí tuyệt đối của Button so với NestedScrollView
                binding.nestedScrollView.setSmoothScrollingEnabled(true);

                int[] location = new int[2];
                binding.tabLayoutHome.getLocationOnScreen(location);

                // Kiểm tra xem Button có nằm ở trên cùng hay không
                if (location[1] <= 0) {
                    // Button đã cuộn lên trên cùng
                    binding.tabLayoutHomeSticky.setVisibility(View.VISIBLE);
                } else {
                    binding.tabLayoutHomeSticky.setVisibility(View.GONE);
                }
            }
        });



    }



    public void initPostRecyclerView() {

    }

    public void initSlider() {
        // Viewpager set slider
        sliders = new ArrayList<>();
        sliders.add(new SliderHome(R.drawable.sliderhome_img1));
        sliders.add(new SliderHome(R.drawable.sliderhome_img2));
        sliders.add(new SliderHome(R.drawable.sliderhome_img3));

        binding.viewPagerSliderHome.setAdapter(new SliderHomeAdapter(sliders, binding.viewPagerHome));
        binding.viewPagerSliderHome.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        CompositePageTransformer pageTransformer = new CompositePageTransformer();
        pageTransformer.addTransformer(new MarginPageTransformer(30));
        pageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        binding.viewPagerSliderHome.setPageTransformer(pageTransformer);
    }



    public void initTabLayout(TabLayout tabLayout) {
        adapter = new FragmentHomeAdapter(getActivity());
        viewPager = binding.viewPagerHome;
        viewPager.setAdapter(adapter);
        binding.viewPagerHome.setUserInputEnabled(false);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, binding.viewPagerHome, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Features");
                    break;
                case 1:
                    tab.setText("Latest");
                    break;
            }
        });
        tabLayoutMediator.attach();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPagerHome.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.viewPagerHome.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    public void initCategory(){
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(0, R.drawable.category_rice, "", "Cơm"));
        categoryList.add(new Category(1, R.drawable.category_noodles, "", "Bún/Phở"));
        categoryList.add(new Category(2, R.drawable.category_drink, "", "Đồ uống"));
        categoryList.add(new Category(3, R.drawable.category_breakfast, "", "Đồ ăn nhanh"));
        categoryList.add(new Category(4, R.drawable.category_saladhealthy, "", "Salad/Healthy"));
        categoryList.add(new Category(5, R.drawable.category_noodles, "", "Category6"));
        categoryList.add(new Category(6, R.drawable.category_noodles, "", "Category7"));
        categoryList.add(new Category(7, R.drawable.category_noodles, "", "Category8"));

        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList, getContext());
        binding.rcvCategory.setAdapter(categoryAdapter);
        binding.rcvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
    }


}
package com.thuanht.eatez.view.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.thuanht.eatez.Adapter.CategoryAdapter;
import com.thuanht.eatez.Adapter.FragmentHomeAdapter;
import com.thuanht.eatez.Adapter.SliderHomeAdapter;
import com.thuanht.eatez.databinding.FragmentHomeBinding;
import com.thuanht.eatez.model.SliderHome;
import com.thuanht.eatez.permission.LocationPermission;
import com.thuanht.eatez.view.Activity.SearchActivity;
import com.thuanht.eatez.viewModel.HomeViewModel;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private FragmentHomeAdapter adapter;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        initSlider();
        initCategory();
        initTabLayout(binding.tabLayoutHome);
        initTabLayout(binding.tabLayoutHomeSticky);
        eventHandler();
        return binding.getRoot();
    }

    private void eventHandler() {
        binding.btnToSearch.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        binding.nestedScrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            // Lấy vị trí tuyệt đối của Button so với NestedScrollView
            binding.nestedScrollView.setSmoothScrollingEnabled(true);

            int[] location = new int[2];
            binding.tabLayoutHome.getLocationOnScreen(location);

            // Kiểm tra xem Button có nằm ở trên cùng hay không
            if (location[1] <= 0) {
                // Button đã cuộn lên trên cùng
                binding.tabLayoutHomeSticky.setVisibility(View.VISIBLE);
                binding.topActionBarHome.setVisibility(View.GONE);
            } else {
                binding.topActionBarHome.setVisibility(View.VISIBLE);
                binding.tabLayoutHomeSticky.setVisibility(View.GONE);
            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        binding.btnLocation.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                LocationPermission.getInstance(requireContext()).requestPermission(requireActivity());
            }
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            Geocoder geocoder = new Geocoder(requireActivity(), Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                assert addresses != null;
                                binding.btnLocation.setText(addresses.get(0).getAddressLine(0));
//                                    Toast.makeText(requireContext(), "Address: " + addresses.get(0).getAddressLine(0), Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
        });
    }
    public void initSlider() {
        // Viewpager set slider
        binding.shimmerSliderHome.startShimmer();
        homeViewModel.getSliderList().observe(requireActivity(), new Observer<List<SliderHome>>() {
            @Override
            public void onChanged(List<SliderHome> sliderHomes) {
                binding.viewPagerSliderHome.setVisibility(View.VISIBLE);
                binding.shimmerSliderHome.stopShimmer();
                binding.shimmerSliderHome.setVisibility(View.GONE);

                binding.viewPagerSliderHome.setAdapter(new SliderHomeAdapter(requireContext(), sliderHomes, binding.viewPagerHome, sliderHome -> {
                    String urlRedirect = sliderHome.getLink();
                    if(!urlRedirect.isEmpty()){
                        Uri uri = Uri.parse(urlRedirect);
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    }
                }));

                binding.viewPagerSliderHome.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
                CompositePageTransformer pageTransformer = new CompositePageTransformer();
                pageTransformer.addTransformer(new MarginPageTransformer(30));
                pageTransformer.addTransformer((page, position) -> {
                    float r = 1 - Math.abs(position);
                    page.setScaleY(0.85f + r * 0.15f);
                });
                binding.viewPagerSliderHome.setPageTransformer(pageTransformer);

                Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        int nextPosition = binding.viewPagerSliderHome.getCurrentItem() + 1;
                        if (nextPosition >= sliderHomes.size()) {
                            nextPosition = 0;
                        }
                        binding.viewPagerSliderHome.setCurrentItem(nextPosition);
                        handler.postDelayed(this, 3000);
                    }
                };
                handler.postDelayed(runnable, 3000);
            }
        });

        homeViewModel.fetchSliderImages();
    }
    public void initTabLayout(TabLayout tabLayout) {
        adapter = new FragmentHomeAdapter(getActivity());
        ViewPager2 viewPager = binding.viewPagerHome;
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        binding.viewPagerHome.setUserInputEnabled(false);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, binding.viewPagerHome, (tab, position) -> {
            switch (position) {
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
    public void initCategory() {
        binding.shimmerCategoryHome.startShimmer();
        homeViewModel.getCategoryList().observe(requireActivity(), categories -> {
            CategoryAdapter categoryAdapter = new CategoryAdapter(categories, getContext());
            binding.rcvCategory.setAdapter(categoryAdapter);
            binding.rcvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
            // Show category + Hide Shimmer
            binding.shimmerCategoryHome.stopShimmer();
            binding.shimmerCategoryHome.setVisibility(View.GONE);
            binding.rcvCategory.setVisibility(View.VISIBLE);
        });

        homeViewModel.fetchCategories();
    }


}
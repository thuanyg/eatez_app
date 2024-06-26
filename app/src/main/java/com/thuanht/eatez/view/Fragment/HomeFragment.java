package com.thuanht.eatez.view.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.thuanht.eatez.Adapter.CategoryAdapter;
import com.thuanht.eatez.Adapter.PostHomeAdapter;
import com.thuanht.eatez.Adapter.SliderHomeAdapter;
import com.thuanht.eatez.Adapter.TrendingAdapter;
import com.thuanht.eatez.LocalData.LocalDataManager;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.FragmentHomeBinding;
import com.thuanht.eatez.interfaceEvent.MyClickItemListener;
import com.thuanht.eatez.model.Category;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.model.SliderHome;

import com.thuanht.eatez.model.Trending;
import com.thuanht.eatez.permission.LocationPermission;
import com.thuanht.eatez.utils.NetworkUtils;
import com.thuanht.eatez.view.Activity.EditProfileActivity;
import com.thuanht.eatez.view.Activity.LoginActivity;
import com.thuanht.eatez.view.Activity.PostCategoryActivity;
import com.thuanht.eatez.view.Activity.PostDetailActivity;
import com.thuanht.eatez.view.Activity.SearchActivity;
import com.thuanht.eatez.view.Activity.TrendDetailActivity;
import com.thuanht.eatez.view.Activity.TrendingActivity;
import com.thuanht.eatez.view.Dialog.DialogUtil;
import com.thuanht.eatez.viewModel.HomeViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private GridLayoutManager gridLayoutManager;
    private List<Post> posts = new ArrayList<>();
    private List<Trending> trendings = new ArrayList<>();
    private PostHomeAdapter adapterPost;
    private int currentPage = 1;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Timer timer;
    private final String KEY_CATEGORY_ID = "category_id_action_intent";
    private final String KEY_DISH_TREND_OBJECT = "trend_obj_action_intent";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        try {
            Glide.with(requireActivity())
                    .load(LocalDataManager.getInstance().getUserLogin().getAvatar_image())
                    .placeholder(R.drawable.onboarding_img_3)
                    .into(binding.avatarHome);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get location
        // GPS Location service
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext()
                , Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            Geocoder geocoder = new Geocoder(requireActivity(), Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if (addresses != null && addresses.size() > 0) {
                                    String city = addresses.get(0).getLocality(); // Lấy tên thành phố
                                    String state = addresses.get(0).getAdminArea(); // Lấy tên tỉnh hoặc bang
                                    binding.btnLocation.setText(state);
                                }
//                                binding.btnLocation.setText(addresses.get(0).getAddressLine(0));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
        } else binding.btnLocation.setText("TP. Hà Nội");

        initCategory();
        initTrending();
        initUIRecyclerViewPost();
        LoadRecyclerViewPost(1);
        eventHandler();
        return binding.getRoot();
    }

    private void initTrending() {
        startShimmer(binding.shimmerTrending);

        TrendingAdapter trendingAdapter = new TrendingAdapter(trendings, trending -> {
            // Send dish (Object) -> Trending Activity
            Intent intent = new Intent(requireActivity(), TrendingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_DISH_TREND_OBJECT, trending);
            intent.putExtras(bundle);
            startActivity(intent);
        }, requireContext());

        // Set adapter - recycler view
        binding.rcvTrending.setAdapter(trendingAdapter);
        binding.rcvTrending.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        homeViewModel.getTrends().observe(requireActivity(), t -> {
            if (t != null) {
                trendings.clear();
                trendings.addAll(t);
                trendingAdapter.notifyDataSetChanged();
            }
            stopShimmer(binding.shimmerTrending);
            binding.rcvTrending.setVisibility(View.VISIBLE);
        });
        homeViewModel.fetchTrending();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSlider();
    }

    public void initUIRecyclerViewPost() {
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.rcvPostHome.setLayoutManager(gridLayoutManager);
        adapterPost = new PostHomeAdapter(requireContext(), posts, post -> {
            goToPostDetailActivity(Integer.parseInt(post.getPostId()));
        });
        binding.rcvPostHome.setAdapter(adapterPost);
    }

    public void LoadRecyclerViewPost(int pageNumber) {
        startShimmer(binding.shimmerHome);
        homeViewModel.getIsLastPageLiveData().observe(getViewLifecycleOwner(), isLastPage -> {
            this.isLastPage = isLastPage;
        });
        homeViewModel.getIsNetworkDisconnect().observe(getViewLifecycleOwner(), isNetworkDisconnet -> {
            if (isNetworkDisconnet) {
                binding.swipeRefreshHome.setVisibility(View.GONE);
                binding.layoutDisconnect.setVisibility(View.VISIBLE);
            }
        });
        homeViewModel.getPosts().observe(requireActivity(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> list) {
                if (list == null) {
                    return;
                }
                isLoading = true;
                if (list.isEmpty()) {
                    // Nếu danh sách trả về từ ViewModel rỗng, có thể là đã tải hết dữ liệu
                    isLastPage = true;
                } else {
                    if (posts.isEmpty()) {
                        // Nếu danh sách posts hiện tại rỗng, thêm tất cả các phần tử từ danh sách mới vào
                        posts.addAll(list);
                        adapterPost.notifyDataSetChanged();
                    } else {
                        // Nếu danh sách posts đã chứa dữ liệu, thêm dữ liệu mới vào và cập nhật RecyclerView
                        int startPosition = posts.size();
                        posts.addAll(list);
                        adapterPost.notifyItemRangeInserted(startPosition, list.size());
                    }
                }
                isLoading = false;
                binding.rcvPostHome.setVisibility(View.VISIBLE);
                stopShimmer(binding.shimmerHome);
            }
        });
        if (NetworkUtils.isNetworkAvailable(requireContext()) && !isLoading) {
            homeViewModel.fetchFeaturePosts(this.requireContext(), pageNumber);
        }
    }

    public void LoadMorePost() {
        if (isLastPage) {
            // Toast.makeText(requireContext(), "Hết dữ liệu. Current Page = " + currentPage, Toast.LENGTH_SHORT).show();
            binding.progressLoadPost.setVisibility(View.GONE);
            binding.tvDataLoadedHome.setVisibility(View.VISIBLE);
            return;
        }
        binding.progressLoadPost.setVisibility(View.VISIBLE);
        binding.tvDataLoadedHome.setVisibility(View.GONE);
        currentPage++;
        if (!isLoading) {
            homeViewModel.fetchFeaturePosts(this.requireContext(), currentPage);
        }
    }


    public void refreshData() {
        //Clear current data
        currentPage = 1;
        posts.clear();
        trendings.clear();
        homeViewModel.setIsLastPage(false);

        //Start shimmer
        startShimmer(binding.shimmerHome);
        startShimmer(binding.shimmerCategoryHome);
        startShimmer(binding.shimmerSliderHome);
        startShimmer(binding.shimmerTrending);

        //Set visibility
        binding.tvDataLoadedHome.setVisibility(View.GONE);
        binding.rcvPostHome.setVisibility(View.GONE);
        binding.rcvTrending.setVisibility(View.GONE);
        binding.rcvCategory.setVisibility(View.GONE);
        binding.viewPagerSliderHome.setVisibility(View.GONE);

        // Reload data
        homeViewModel.fetchSliderImages();
        homeViewModel.fetchCategories();
        homeViewModel.fetchTrending();
        homeViewModel.fetchFeaturePosts(requireContext(), 1);
    }

    public void startShimmer(ShimmerFrameLayout shimmerFrameLayout) {
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
    }

    public void stopShimmer(ShimmerFrameLayout shimmerFrameLayout) {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    private void goToPostDetailActivity(int postid) {
        Intent intent = new Intent(requireContext(), PostDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("postid", postid);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @SuppressLint("RestrictedApi")
    private void eventHandler() {
        binding.avatarHome.setOnClickListener(v -> {
            if (DialogUtil.checkLoginAndRequired(requireActivity())) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        binding.btnToSearch.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        ViewTreeObserver.OnScrollChangedListener onScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) binding.nestedScrollViewHome.getChildAt(binding.nestedScrollViewHome.getChildCount() - 1);
                int diff = (view.getBottom() - (binding.nestedScrollViewHome.getHeight() + binding.nestedScrollViewHome.getScrollY()));
                if (diff == 0) {
                    LoadMorePost();
                }
            }
        };
        binding.nestedScrollViewHome.getViewTreeObserver().addOnScrollChangedListener(onScrollChangedListener);

        binding.btnLocation.setOnClickListener(v -> {
            DialogUtil.showStandardDialog(requireContext(), "Comming soon", "Ứng dụng hiện tại chỉ hỗ trợ khu vực TP. Hà Nội. "
                            + "Hãy theo dõi những bản cập nhật mới nhất của chúng tôi để thực hiện tác vụ này!",
                    "OK", "Hủy", new DialogUtil.DialogClickListener() {
                        @Override
                        public void onPositiveButtonClicked(Dialog dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void onNegativeButtonClicked() {

                        }
                    });
        });

        // Swipe refresh data
        binding.swipeRefreshHome.setOnRefreshListener(() -> {
            refreshData();
            binding.swipeRefreshHome.setRefreshing(false);
        });

        binding.btnSeeAllTrending.setOnClickListener(v -> {
            startActivity(new Intent(requireActivity(), TrendDetailActivity.class));
        });

        binding.btnTryAgain.setOnClickListener(v -> {
            binding.layoutDisconnect.setVisibility(View.GONE);
            binding.swipeRefreshHome.setVisibility(View.VISIBLE);
            refreshData();
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

                binding.viewPagerSliderHome.setAdapter(new SliderHomeAdapter(getActivity(), sliderHomes, binding.viewPagerSliderHome, sliderHome -> {
                    String urlRedirect = sliderHome.getLink();
                    if (!urlRedirect.isEmpty()) {
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


                if (timer != null) {
                    stopAutoSlider();
                }
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            int nextPosition = binding.viewPagerSliderHome.getCurrentItem() + 1;
                            if (nextPosition >= sliderHomes.size()) {
                                nextPosition = 0;
                            }
                            binding.viewPagerSliderHome.setCurrentItem(nextPosition);
                        });
                    }
                }, 3000, 3000);
            }
        });

        homeViewModel.fetchSliderImages();
    }

    private void stopAutoSlider() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initCategory() {
        startShimmer(binding.shimmerCategoryHome);
        homeViewModel.getCategoryList().observe(requireActivity(), categories -> {
            CategoryAdapter categoryAdapter = new CategoryAdapter(categories, requireContext(), new MyClickItemListener<Category>() {
                @Override
                public void onClick(Category category) {
                    Intent intent = new Intent(requireActivity(), PostCategoryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_CATEGORY_ID, category);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            binding.rcvCategory.setAdapter(categoryAdapter);
            binding.rcvCategory.setLayoutManager(new GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false));
//            binding.rcvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
            // Show category + Hide Shimmer
            stopShimmer(binding.shimmerCategoryHome);
            binding.rcvCategory.setVisibility(View.VISIBLE);
        });
        homeViewModel.fetchCategories();
    }
}
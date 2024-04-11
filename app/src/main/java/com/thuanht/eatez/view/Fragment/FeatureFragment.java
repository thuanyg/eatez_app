package com.thuanht.eatez.view.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.thuanht.eatez.Adapter.PostFeatureAdapter;
import com.thuanht.eatez.databinding.FragmentFeatureBinding;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.pagination.EndlessRecyclerViewScrollListener;
import com.thuanht.eatez.pagination.PaginationOnScrollListener;
import com.thuanht.eatez.utils.NetworkUtils;
import com.thuanht.eatez.view.Activity.PostDetailActivity;
import com.thuanht.eatez.viewModel.FeatureViewModel;

import java.util.ArrayList;
import java.util.List;

public class FeatureFragment extends Fragment {
    private FeatureViewModel featureViewModel;
    private FragmentFeatureBinding binding;
    private PostFeatureAdapter adapter;

    private GridLayoutManager gridLayoutManager;

    private List<Post> posts = new ArrayList<>();
    private boolean isLastPage = false;
    private boolean isLoading = false;

    private int currentPage = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeatureBinding.inflate(inflater, container, false);
        featureViewModel = new ViewModelProvider(this).get(FeatureViewModel.class);
        initUIRecyclerViewPost();
        // Load dữ liệu ở trang đầu tiên
        LoadRecyclerViewPost(1);
        eventHandler();
        return binding.getRoot();
    }

    private void eventHandler() {

    }


    private void loadNextPage() {
        isLoading = true;
        currentPage++;

    }


    private void goToPostDetailActivity(Post post) {
        Intent intent = new Intent(requireContext(), PostDetailActivity.class);
        if (post != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("post", post);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(requireContext(), "Something wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.rcvFeatures.requestLayout();
    }

    public void initUIRecyclerViewPost() {
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.rcvFeatures.setLayoutManager(gridLayoutManager);
        adapter = new PostFeatureAdapter(requireContext(), posts, post -> {
            goToPostDetailActivity(post);
        });
        binding.rcvFeatures.setAdapter(adapter);
    }

    public void LoadRecyclerViewPost(int pageNumber) {
        startShimmer();

        featureViewModel.getIsLastPageLiveData().observe(getViewLifecycleOwner(), isLastPage -> {
            this.isLastPage = isLastPage;
        });

        featureViewModel.getPosts().observe(requireActivity(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> list) {
                if (list == null) {
                    return;
                }
                isLoading = true;
                Log.d("TagDev", "Current page: " + currentPage + " List: "
                        + list.get(0).toString() + " - Size return api: " + String.valueOf(list.size()));
                if (list.isEmpty()) {
                    // Nếu danh sách trả về từ ViewModel rỗng, có thể là đã tải hết dữ liệu
                    isLastPage = true;
                } else {
                    if (posts.isEmpty()) {
                        // Nếu danh sách posts hiện tại rỗng, thêm tất cả các phần tử từ danh sách mới vào
                        posts.addAll(list);
                        adapter.notifyDataSetChanged();
                    } else {
                        // Nếu danh sách posts đã chứa dữ liệu, thêm dữ liệu mới vào và cập nhật RecyclerView
                        int startPosition = posts.size();
                        posts.addAll(list);
                        adapter.notifyItemRangeInserted(startPosition, list.size());
                    }
                }

                isLoading = false;
                binding.rcvFeatures.setVisibility(View.VISIBLE);
                stopShimmer();
            }
        });
        if(NetworkUtils.isNetworkAvailable(requireContext())){
            featureViewModel.fetchFeaturePosts(this.requireContext(), pageNumber);
        }
    }


    public void LoadMoreFeaturePost(){
        if(isLastPage){
            // Toast.makeText(requireContext(), "Hết dữ liệu. Current Page = " + currentPage, Toast.LENGTH_SHORT).show();
            binding.progressLoadPost.setVisibility(View.GONE);
            binding.tvDataLoadedFeature.setVisibility(View.VISIBLE);
            return;
        }
        binding.progressLoadPost.setVisibility(View.VISIBLE);
        binding.tvDataLoadedFeature.setVisibility(View.GONE);
        currentPage++;
        if(!isLoading){
            featureViewModel.fetchFeaturePosts(this.requireContext(), currentPage);
        }
    }

    // Refresh data
    public void refreshData(){
        currentPage = 1;
        posts.clear();
        startShimmer();
        binding.progressLoadPost.setVisibility(View.VISIBLE);
        binding.tvDataLoadedFeature.setVisibility(View.GONE);
        featureViewModel.setIsLastPage(false);
        featureViewModel.fetchFeaturePosts(requireContext(), 1);
    }

    // Shimmer
    public void startShimmer() {
        binding.shimmerHome.startShimmer();
        binding.shimmerHome.setVisibility(View.VISIBLE);
    }

    public void stopShimmer() {
        binding.shimmerHome.stopShimmer();
        binding.shimmerHome.setVisibility(View.GONE);
    }

    //Getter/Setter
    public FeatureViewModel getFeatureViewModel() {
        return featureViewModel;
    }

    public RecyclerView getRecyclerView() {
        return binding.rcvFeatures;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
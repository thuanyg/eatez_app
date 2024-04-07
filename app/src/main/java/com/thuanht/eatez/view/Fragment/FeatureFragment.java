package com.thuanht.eatez.view.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.thuanht.eatez.Adapter.PostFeatureAdapter;
import com.thuanht.eatez.databinding.FragmentFeatureBinding;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.view.Activity.PostDetailActivity;
import com.thuanht.eatez.viewModel.FeatureViewModel;
public class FeatureFragment extends Fragment {
    private FeatureViewModel featureViewModel;
    private FragmentFeatureBinding binding;
    private PostFeatureAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeatureBinding.inflate(inflater, container, false);
        featureViewModel = new ViewModelProvider(this).get(FeatureViewModel.class);
        initRecyclerViewPost();
        callApiGetPosts();
        return binding.getRoot();
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
    public void initRecyclerViewPost() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.rcvFeatures.setLayoutManager(gridLayoutManager);
    }
    public void callApiGetPosts() {
        binding.shimmerHome.startShimmer();
        featureViewModel.getPosts().observe(requireActivity(), list -> {
            adapter = new PostFeatureAdapter(list, p -> {
                goToPostDetailActivity(p);
            });
            binding.rcvFeatures.setAdapter(adapter);
            binding.rcvFeatures.setVisibility(View.VISIBLE);
            binding.shimmerHome.stopShimmer();
            binding.shimmerHome.setVisibility(View.GONE);
        });

        featureViewModel.fetchPosts(this.requireContext());
    }
    //Getter/Setter
}
package com.thuanht.eatez.view.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thuanht.eatez.Adapter.PostLatestAdapter;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.FragmentLatestBinding;
import com.thuanht.eatez.model.Post;

import java.util.ArrayList;
import java.util.List;

public class LatestFragment extends Fragment {
    private FragmentLatestBinding bd;
    private PostLatestAdapter adapter;
    private List<Post> posts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bd = FragmentLatestBinding.inflate(inflater, container, false);
        initPostLatest();
        return bd.getRoot();
    }

    private void initPostLatest() {
        bd.rcvLatest.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));
        posts = new ArrayList<>();
        adapter = new PostLatestAdapter(posts, requireContext());
        bd.rcvLatest.setAdapter(adapter);
        bd.shimmerHomeLatest.startShimmer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Thực hiện các công việc nặng ở đây
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Sau khi hoàn thành, sử dụng Handler để đưa công việc về giao diện người dùng (UI thread)
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        bd.shimmerHomeLatest.stopShimmer();
                        bd.shimmerHomeLatest.setVisibility(View.GONE);
                        bd.rcvLatest.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }

}
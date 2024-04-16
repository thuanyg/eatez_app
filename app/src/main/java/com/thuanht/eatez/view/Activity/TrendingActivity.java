package com.thuanht.eatez.view.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivityTrendingBinding;

public class TrendingActivity extends AppCompatActivity {
    ActivityTrendingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrendingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
    }
}
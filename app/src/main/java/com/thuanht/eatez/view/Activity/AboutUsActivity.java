package com.thuanht.eatez.view.Activity;

import android.os.Bundle;
import android.text.Html;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivityAboutusBinding;

public class AboutUsActivity extends AppCompatActivity {
    private ActivityAboutusBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutusBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.toolbarAboutUs.setNavigationOnClickListener(v -> finish());
        binding.tvAboutUs.setText(Html.fromHtml("<h1>About Us</h1>\n" +
                "<p>Welcome to Eat Ez!</p>\n" +
                "<p>Eat Ez is your ultimate destination for discovering and exploring delicious foods through authentic reviews. Our platform is designed to help you find the perfect dining experiences tailored to your tastes and preferences.</p>\n" +
                "<h2>Our Mission</h2>\n" +
                "<p>At Eat Ez, our mission is to connect food enthusiasts with exceptional culinary experiences. We believe that every meal should be a delightful adventure, and we're passionate about making it easy for you to discover new flavors, cuisines, and restaurants.</p>\n" +
                "<h2>Why Choose Eat Ez?</h2>\n" +
                "<p>With Eat Ez, you can:</p>\n" +
                "<ul>\n" +
                "  <li><strong>Discover:</strong> Explore a diverse range of dining options, from cozy cafes to upscale restaurants, and uncover hidden culinary gems in your area.</li>\n" +
                "  <li><strong>Find:</strong> Use our powerful search and filtering tools to quickly find the perfect spot for any occasion, whether it's a romantic dinner, a casual brunch, or a quick bite on the go.</li>\n" +
                "  <li><strong>Read Reviews:</strong> Benefit from genuine reviews and ratings from fellow foodies who have tried and tested the dishes firsthand. Get insider tips and recommendations to ensure a memorable dining experience every time.</li>\n" +
                "  <li><strong>Share Your Experience:</strong> Join our community of food lovers and share your own dining adventures. Leave reviews, upload mouthwatering photos, and contribute to the collective knowledge of food enthusiasts worldwide.</li>\n" +
                "</ul>\n" +
                "<h2>Contact Us</h2>\n" +
                "<p>Got questions, feedback, or suggestions? We'd love to hear from you!</p>\n" +
                "<ul>\n" +
                "  <li><strong>Email:</strong> thuanht.nuce@gmail.com </li>\n" +
                "  <li><strong>Phone:</strong> +84 373 897 359</li>\n" +
                "  <li><strong>Address:</strong> HUCE - 55 Giai Phong, Hai Ba Trung, Ha Noi, Viet Nam </li>\n" +
                "</ul>\n" +
                "<p>Follow us on social media for the latest updates and mouthwatering food photos:</p>\n" +
                "<ul>\n" +
                "  <li><a href=\"#\">Facebook</a></li>\n" +
                "  <li><a href=\"#\">Instagram</a></li>\n" +
                "</ul>\n" +
                "<p>Thank you for choosing Eat Ez! Let's embark on a culinary journey together.</p>"));
    }
}
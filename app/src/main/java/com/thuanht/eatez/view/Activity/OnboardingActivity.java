package com.thuanht.eatez.view.Activity;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thuanht.eatez.Adapter.OnboardingViewPagerAdapter;
import com.thuanht.eatez.LocalData.LocalDataManager;
import com.thuanht.eatez.R;
import com.thuanht.eatez.database.database.AppDatabase;
import com.thuanht.eatez.database.entity.Suggestion;
import com.thuanht.eatez.viewModel.SuggestViewModel;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager slideViewPager;

    private OnboardingViewPagerAdapter obViewPagerAdapter;
    private SuggestViewModel suggestViewModel;
    private TextView[] dots;
    private LinearLayout dotIndicator;
    private TextView btn_skip, btn_next;

    private ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setDotIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_onboarding);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        suggestViewModel = new ViewModelProvider(this).get(SuggestViewModel.class);

        Intent intent = new Intent(this, GetStartedActivity.class);
        boolean isFirstInstall = LocalDataManager.getInstance().checkIsFirstInstall();

        if (!isFirstInstall) {
            // Lần đầu vào ứng dụng
            ProcessData();
            btn_skip = findViewById(R.id.btn_skip);
            btn_next = findViewById(R.id.btn_next);

            btn_next.setOnClickListener(v -> {
                if(getItem(0) < 2){
                    slideViewPager.setCurrentItem(getItem(1), true);
                } else {
                    startActivity(intent);
                    finish();
                }
            });

            btn_skip.setOnClickListener(v -> {
                startActivity(intent);
                finish();
            });

            slideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
            dotIndicator = (LinearLayout) findViewById(R.id.dotIndicator);
            obViewPagerAdapter = new OnboardingViewPagerAdapter(this);
            slideViewPager.setAdapter(obViewPagerAdapter);

            setDotIndicator(0);
            slideViewPager.addOnPageChangeListener(viewPagerListener);
            LocalDataManager.getInstance().setValueForFirstInstall(true);
        } else {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        }

    }

    private void ProcessData() {
        // Observe to fetch suggestion search value from API to Database
        suggestViewModel.getListSuggestions().observe(this, suggestions -> {
            if(suggestions != null){
                List<Suggestion> suggests_entity = new ArrayList<>();
                for (com.thuanht.eatez.model.Suggestion s: suggestions) {
                    Suggestion newSuggestionEntity = new Suggestion(0, s.getSuggestValue());
                    suggests_entity.add(newSuggestionEntity);
                }
                List<Long> row_affects = AppDatabase.getInstance(this).suggestionDAO().insertAll(suggests_entity);
            }
        });
        // Fetch suggestion search value from API to Database
        suggestViewModel.fetchSuggestionValue();
    }

    private void setDotIndicator(int position){
        dots = new TextView[3];
        dotIndicator.removeAllViews();

        for (int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(30);
            dots[i].setTextColor(getResources().getColor(R.color.gray, getApplicationContext().getTheme()));
            dotIndicator.addView(dots[i]);
        }

        dots[position].setTextColor(getResources().getColor(R.color.button_color, getApplicationContext().getTheme()));
    }

    private int getItem(int i){
        return slideViewPager.getCurrentItem() + i;
    }
}
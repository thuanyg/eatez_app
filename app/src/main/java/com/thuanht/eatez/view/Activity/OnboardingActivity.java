package com.thuanht.eatez.view.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thuanht.eatez.Adapter.OnboardingViewPagerAdapter;
import com.thuanht.eatez.LocalData.LocalDataManager;
import com.thuanht.eatez.R;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager slideViewPager;

    private OnboardingViewPagerAdapter obViewPagerAdapter;

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


        Intent intent = new Intent(this, GetStartedActivity.class);
        boolean isFirstInstall = LocalDataManager.getInstance().checkIsFirstInstall();

        if (!isFirstInstall) {
            // Lần đầu vào ứng dụng
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
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }

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
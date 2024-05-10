package com.thuanht.eatez.view.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.thuanht.eatez.R;

public class GetStartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        TextView btn_start = (TextView) findViewById(R.id.btn_start);

        btn_start.setOnClickListener(v -> {
            Intent intent = new Intent(GetStartedActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}

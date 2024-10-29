package com.tbi.dailyreminder.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tbi.dailyreminder.R;
import com.tbi.dailyreminder.ui.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startIntent();
    }

    private void startIntent() {

        int SCREEN_DELAY = 3 * 1000; // 3sec

        new Handler().postDelayed(() -> {

            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();

        }, SCREEN_DELAY);
    }}
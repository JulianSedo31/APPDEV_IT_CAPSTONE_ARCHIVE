package com.example.appdev_buksu_it_capstone_archives;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class SplashActivity extends AppCompatActivity {
    private ImageView logoImage;
    private TextView titleText, subtitleText;
    private CircularProgressIndicator progressBar;
    private static final int SPLASH_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initializeViews();
        startAnimations();
        navigateToIntro();
    }

    private void initializeViews() {
        logoImage = findViewById(R.id.ivLogo);
        titleText = findViewById(R.id.tvSplashTitle);
        subtitleText = findViewById(R.id.tvSplashSubtitle);
        progressBar = findViewById(R.id.progressBar);

        // Set initial alpha
        logoImage.setAlpha(0f);
        titleText.setAlpha(0f);
        subtitleText.setAlpha(0f);
    }

    private void startAnimations() {
        // Logo animation
        AlphaAnimation logoAnim = new AlphaAnimation(0f, 1f);
        logoAnim.setDuration(1000);
        logoAnim.setFillAfter(true);
        logoImage.startAnimation(logoAnim);

        // Title animation
        AlphaAnimation titleAnim = new AlphaAnimation(0f, 1f);
        titleAnim.setDuration(1000);
        titleAnim.setStartOffset(300);
        titleAnim.setFillAfter(true);
        titleText.startAnimation(titleAnim);

        // Subtitle animation
        AlphaAnimation subtitleAnim = new AlphaAnimation(0f, 1f);
        subtitleAnim.setDuration(1000);
        subtitleAnim.setStartOffset(600);
        subtitleAnim.setFillAfter(true);
        subtitleText.startAnimation(subtitleAnim);

        // Progress bar animation
        progressBar.setProgress(0);
        progressBar.setMax(100);
        animateProgressBar();
    }

    private void animateProgressBar() {
        new Handler().postDelayed(new Runnable() {
            int progress = 0;
            @Override
            public void run() {
                if (progress <= 100) {
                    progressBar.setProgress(progress);
                    progress += 2;
                    new Handler().postDelayed(this, SPLASH_DURATION / 50);
                }
            }
        }, 0);
    }

    private void navigateToIntro() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }, SPLASH_DURATION);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clear any pending callbacks
        new Handler().removeCallbacksAndMessages(null);
    }
}

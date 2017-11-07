package com.cardee.splash;

import com.cardee.R;
import com.cardee.auth.preview.PreviewActivity;
import com.cardee.custom.CarProgressBar;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.owner_home.view.OwnerHomeActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class SplashActivity extends AppCompatActivity {

    private static final int LOADING_DURATION = 2000;

    private CarProgressBar mLoadingProgress;

    private ValueAnimator mAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mLoadingProgress = (CarProgressBar) findViewById(R.id.loading_progress);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, PreviewActivity.class));
                finish();
                // TODO: 10/18/17 Loading car animation
            }
        }, 3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLoadingProgress.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                startAnimation();
            }
        });
    }

    private void startAnimation() {
        if (mAnimator != null) {
            return;
        }
        mAnimator = ValueAnimator.ofFloat(0, 100);
        mAnimator.setDuration(LOADING_DURATION);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float value = (Float) valueAnimator.getAnimatedValue();
                mLoadingProgress.setProgress(value);
            }
        });
        mAnimator.start();
    }
}

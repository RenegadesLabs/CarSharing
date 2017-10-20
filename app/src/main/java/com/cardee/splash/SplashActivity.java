package com.cardee.splash;

import com.cardee.R;
import com.cardee.auth.preview.PreviewActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, PreviewActivity.class));
                finish();
            }
        }, 3000);
        // TODO: 10/18/17 Loading car animation
    }
}

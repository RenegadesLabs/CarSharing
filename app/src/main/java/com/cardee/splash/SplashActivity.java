package com.cardee.splash;

import com.cardee.R;
import com.cardee.auth.preview.PreviewActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startActivity(new Intent(this, PreviewActivity.class));
        finish();
        // TODO: 10/18/17 Loading car animation
    }
}

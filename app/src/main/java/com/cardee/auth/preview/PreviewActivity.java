package com.cardee.auth.preview;

import com.cardee.R;
import com.cardee.auth.login.LoginActivity;
import com.cardee.auth.register.view.RegisterActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
    }

    public void onLogInClicked(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void onSignUpClicked(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}

package com.cardee.auth.preview;

import com.cardee.R;
import com.cardee.auth.login.view.LoginActivity;
import com.cardee.auth.register.view.RegisterActivity;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.owner_home.view.OwnerHomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);
        checkLogged();
    }

    @OnClick(R.id.b_previewLogin)
    public void onLogInClicked(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.b_previewSignUp)
    public void onSignUpClicked(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void checkLogged() {
        if (AccountManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, OwnerHomeActivity.class));
            finish();
        }
    }
}

package com.cardee.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;

import com.cardee.R;
import com.cardee.auth.register.view.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_loginEmail)
    AppCompatEditText loginEmailEdit;

    @BindView(R.id.et_loginPassword)
    TextInputEditText loginPassEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.b_loginGoToRegister)
    public void onGoToRegisterClicked() {
        new Intent(this, RegisterActivity.class);
        finish();
    }

    @OnClick(R.id.b_loginLogin)
    public void onLoginClicked() {

    }

    @OnClick(R.id.tv_loginForgotPassword)
    public void onForgotPassClicked() {}

}

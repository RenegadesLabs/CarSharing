package com.cardee.auth.register.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.cardee.R;
import com.cardee.auth.login.LoginActivity;
import com.cardee.auth.register.RegisterContract;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.RegisterView {

    private RegisterFirstStepFragment mFirstStepFragment;
    private RegisterFinalStepFragment mFinalStepFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initFragments();
    }

    private void initFragments() {
        mFragmentManager = getSupportFragmentManager();

        mFirstStepFragment = new RegisterFirstStepFragment();
        mFirstStepFragment.setViewListener(this);

        mFinalStepFragment = new RegisterFinalStepFragment();
        mFinalStepFragment.setViewListener(this);

        mFragmentManager.beginTransaction()
                .replace(R.id.container, mFirstStepFragment)
                .commit();
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int messageId) {

    }

    @Override
    public void onLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onSignUp() {
        mFragmentManager.beginTransaction()
                .replace(R.id.container, mFinalStepFragment)
                .commit();
    }

    @Override
    public void onBackToFirstStep() {
        mFragmentManager.beginTransaction()
                .replace(R.id.container, mFirstStepFragment)
                .commit();
    }

    @Override
    public void onFacebook() {

    }

    @Override
    public void onGoogle() {

    }

}

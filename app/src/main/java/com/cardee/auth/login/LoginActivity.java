package com.cardee.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Toast;

import com.cardee.BuildConfig;
import com.cardee.R;
import com.cardee.auth.register.view.RegisterActivity;
import com.cardee.domain.owner.usecase.Login;
import com.cardee.owner_home.view.OwnerHomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.et_loginEmail)
    AppCompatEditText loginEmailEdit;

    @BindView(R.id.et_loginPassword)
    TextInputEditText loginPassEdit;

    @BindView(R.id.l_loginPassword)
    TextInputLayout loginPassLayout;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this);
    }

    @OnClick(R.id.b_loginGoToRegister)
    public void onGoToRegisterClicked() {
        new Intent(this, RegisterActivity.class);
        finish();
    }

    @OnClick(R.id.b_loginLogin)
    public void onLoginClicked() {
        if (isFieldsNotEmpty()) {
            mPresenter.login(loginEmailEdit.getText().toString(),
                    loginPassEdit.getText().toString());
        }
    }

    @OnClick(R.id.tv_loginForgotPassword)
    public void onForgotPassClicked() {}

    private boolean isFieldsNotEmpty() {
        String err = getResources().getString(R.string.email_pass_empty_error);
        if (loginEmailEdit.getText().toString().equals("")) {
            loginEmailEdit.setError(err);
            return false;
        } else if (loginPassEdit.getText().toString().equals("")) {
            loginPassLayout.setError(err);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, OwnerHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

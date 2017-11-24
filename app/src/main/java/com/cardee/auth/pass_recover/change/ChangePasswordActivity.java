package com.cardee.auth.pass_recover.change;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.auth.login.view.LoginActivity;
import com.cardee.data_source.util.DialogHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;


public class ChangePasswordActivity extends AppCompatActivity implements ChangePassView {

    @BindView(R.id.et_changePass)
    AppCompatEditText newPasswordET;

    @BindView(R.id.et_changePassConfirm)
    AppCompatEditText newPasswordConfirmET;

    private String key;

    private ProgressDialog mProgress;

    private ChangePasswordPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_change);
        ButterKnife.bind(this);
        handleIntent(getIntent());
        mProgress = DialogHelper.getProgressDialog(this, getString(R.string.loading), false);
        mPresenter = new ChangePasswordPresenter(this);
    }

    @OnClick(R.id.b_changePass)
    public void onChangePasswordClicked() {
        if (isPassInputCorrect()) {
            mPresenter.changePassword(key, newPasswordET.getText().toString(),
                    newPasswordConfirmET.getText().toString());
        }
    }

    @OnClick(R.id.b_changeBack)
    public void onBackClicked() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @OnTextChanged(R.id.et_changePassConfirm)
    public void onPassConfirmTextChanged(CharSequence text) {
        if (newPasswordET.getText().toString().isEmpty()
                || newPasswordET.getText().length() < 3) {
            newPasswordET.setError(getString(R.string.change_pass_edit_error));
        } else {
            newPasswordET.setError(null);
        }
    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {
            Uri uri = Uri.parse(appLinkData.toString());
            key = uri.getLastPathSegment();
        }
    }

    private boolean isPassInputCorrect() {
        if (newPasswordET.getText().toString().isEmpty()
                || newPasswordET.getText().length() < 3) {
            newPasswordET.setError(getString(R.string.change_pass_edit_error));
            return false;
        }
        if (newPasswordConfirmET.getText().toString().isEmpty()
                || newPasswordConfirmET.getText().length() < 3) {
            newPasswordConfirmET.setError(getString(R.string.change_pass_edit_error));
            return false;
        }
        if (!newPasswordET.getText().toString()
                .equals(newPasswordConfirmET.getText().toString())) {
            newPasswordConfirmET.setError(getString(R.string.change_pass_edit_error_matching));
            return false;
        }
        return true;
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            mProgress.show();
        } else {
            mProgress.dismiss();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessfullyChanged() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}

package com.cardee.owner_change_pass.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.auth.pass_recover.send_email.SendEmailActivity;
import com.cardee.data_source.util.DialogHelper;
import com.cardee.owner_change_pass.presenter.ChangePassPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePassActivity extends AppCompatActivity implements ChangePassView {

    private ProgressDialog mProgress;
    private Toast mCurrentToast;
    private ChangePassPresenter mPresenter;

    @BindView(R.id.change_pass_container)
    View mContainer;

    @BindView(R.id.et_current_pass)
    AppCompatEditText mCurrentPass;

    @BindView(R.id.et_new_pass)
    AppCompatEditText mNewPass;

    @BindView(R.id.et_repeat_pass)
    AppCompatEditText mRepeatPass;

    @BindView(R.id.b_save)
    AppCompatButton mSaveButton;

    @BindView(R.id.b_cancel)
    AppCompatButton mCancelButton;

    @BindView(R.id.tv_forgotten_pass)
    TextView mForgottenPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        ButterKnife.bind(this);

        initToolBar();
        mProgress = DialogHelper.getProgressDialog(this, getString(R.string.loading), false);
        mPresenter = new ChangePassPresenter(this);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    @OnClick(R.id.b_save)
    public void onSavePasswordClicked() {
        if (isPassInputCorrect()) {
            mPresenter.changePassword(mCurrentPass.getText().toString(),
                    mNewPass.getText().toString());
        }
    }

    @OnClick(R.id.b_cancel)
    public void onCancelClicked() {
        onBackPressed();
    }

    @OnClick(R.id.tv_forgotten_pass)
    public void onForgetPassClicked() {
        Intent intent = new Intent(this, SendEmailActivity.class);
        startActivity(intent);
    }

    private boolean isPassInputCorrect() {
        if ((mCurrentPass.getText().toString().isEmpty()) || (mCurrentPass.getText().length() < 3)) {
            mCurrentPass.setError(getString(R.string.change_pass_edit_error));
            return false;
        }
        if ((mNewPass.getText().toString().isEmpty()) || (mNewPass.getText().length() < 3)) {
            mNewPass.setError(getString(R.string.change_pass_edit_error));
            return false;
        }
        if ((mRepeatPass.getText().toString().isEmpty()) || (mRepeatPass.getText().length() < 3)) {
            mRepeatPass.setError(getString(R.string.change_pass_edit_error));
            return false;
        }
        if (!mNewPass.getText().toString().equals(mRepeatPass.getText().toString())) {
            mRepeatPass.setError(getString(R.string.change_pass_edit_error_matching));
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
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }

    @Override
    public void showMessage(int messageId) {
        showMessage(getString(messageId));
    }


    @Override
    public void onSuccessfullyChanged() {
        showMessage(R.string.change_pass_success);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

}

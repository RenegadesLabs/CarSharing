package com.cardee.auth.pass_recover;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.data_source.util.DialogHelper;
import com.cardee.mvp.BaseView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PassRecoverActivity extends AppCompatActivity implements BaseView {

    @BindView(R.id.et_recoverEmail)
    public AppCompatEditText recoverEmailET;

    private ProgressDialog mProgress;

    private PassRecoverPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_pass);
        ButterKnife.bind(this);
        mProgress = DialogHelper.getProgressDialog(this, getString(R.string.loading), false);
        mPresenter = new PassRecoverPresenter(this);
    }

    @OnClick(R.id.b_recoverBack)
    public void onBackArrowPressed() {
        onBackPressed();
    }

    @OnClick(R.id.b_recoverSendEmail)
    public void onSendEmailClicked() {
        if (recoverEmailET.getText().toString().isEmpty())
            return;

        mPresenter.sendEmail(recoverEmailET.getText().toString());
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
}

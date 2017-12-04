package com.cardee.owner_change_pass.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.auth.pass_recover.change.ChangePassView;
import com.cardee.auth.pass_recover.change.ChangePasswordPresenter;
import com.cardee.data_source.util.DialogHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePassActivity extends AppCompatActivity implements ChangePassView {

    private ProgressDialog mProgress;
    private Toast mCurrentToast;
    private ChangePasswordPresenter mPresenter;

    @BindView(R.id.change_pass_container)
    View mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        ButterKnife.bind(this);

        initToolBar();
        mProgress = DialogHelper.getProgressDialog(this, getString(R.string.loading), false);
        mPresenter = new ChangePasswordPresenter(this);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            mContainer.setVisibility(View.GONE);
            mProgress.show();
            return;
        }
        mContainer.setVisibility(View.VISIBLE);
        mProgress.dismiss();
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

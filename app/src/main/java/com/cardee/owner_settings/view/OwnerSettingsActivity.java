package com.cardee.owner_settings.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.owner_notifications.view.OwnerNotifActivity;
import com.cardee.owner_settings.presenter.OwnerSettingsPresenter;
import com.cardee.renter_notifications.view.RenterNotifActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OwnerSettingsActivity extends AppCompatActivity implements OwnerSettingsView {

    private OwnerSettingsPresenter mPresenter;
    private Toast mCurrentToast;

    @BindView(R.id.renter_card)
    View mRenterCard;

    @BindView(R.id.owner_card)
    View mOwnerCard;

    @BindView(R.id.settings_progress)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_settings);
        ButterKnife.bind(this);

        initToolbar();
        mPresenter = new OwnerSettingsPresenter(this);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    @OnClick(R.id.renter_card)
    public void onRenterNotifClicked() {
        Intent intent = new Intent(this, RenterNotifActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.owner_card)
    public void onOwnerNotifClicked() {
        Intent intent = new Intent(this, OwnerNotifActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}

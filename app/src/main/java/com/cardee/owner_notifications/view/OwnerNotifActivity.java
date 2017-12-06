package com.cardee.owner_notifications.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.owner_notifications.presenter.OwnerNotifPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OwnerNotifActivity extends AppCompatActivity implements OwnerNotifView {

    private OwnerNotifPresenter mPresenter;
    private Toast mCurrentToast;

    @BindView(R.id.tv_handover_reminder)
    TextView mHandover;

    @BindView(R.id.tv_return_reminder)
    TextView mReturn;

    @BindView(R.id.sw_booking_request)
    SwitchCompat mBookingReqSwitch;

    @BindView(R.id.sw_instant_booking)
    SwitchCompat mInstantBookSwitch;

    @BindView(R.id.sw_reminders)
    SwitchCompat mRemindersSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_notif);
        ButterKnife.bind(this);

        initToolbar();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new OwnerNotifPresenter(this);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    @OnClick(R.id.tv_handover_reminder)
    public void onHandoverReminderClicked() {
        mPresenter.onHandoverReminderClicked(this);
    }

    @OnClick(R.id.tv_return_reminder)
    public void onReturnReminderClicked() {
        mPresenter.onReturnReminderClicked(this);
    }

    @OnClick(R.id.sw_booking_request)
    public void onBookingRequestSwitched() {
        mPresenter.onBookingRequestSwitched();
    }

    @OnClick(R.id.sw_instant_booking)
    public void onInstantBookSwitched() {
        mPresenter.onInstantBookSwitched();
    }

    @OnClick(R.id.sw_reminders)
    public void onRemindersSwitched() {
        mPresenter.onRemindersSwitched();
    }


    @Override
    public void showProgress(boolean show) {

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

package com.cardee.renter_notifications.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.renter_notifications.presenter.RenterNotifPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class RenterNotifActivity extends AppCompatActivity implements RenterNotifView {

    private RenterNotifPresenter mPresenter;
    private Toast mCurrentToast;

    @BindView(R.id.notif_container)
    View mContainer;

    @BindView(R.id.notif_progress)
    ProgressBar mProgressBar;

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
        setContentView(R.layout.activity_renter_notif);
        ButterKnife.bind(this);

        initToolbar();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new RenterNotifPresenter(this);
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

    @OnCheckedChanged(R.id.sw_booking_request)
    public void onBookingRequestSwitched(CompoundButton button, boolean checked) {
        mPresenter.onBookingRequestSwitched(checked);
    }

    @OnCheckedChanged(R.id.sw_instant_booking)
    public void onInstantBookSwitched(CompoundButton button, boolean checked) {
        mPresenter.onInstantBookSwitched(checked);
    }

    @OnCheckedChanged(R.id.sw_reminders)
    public void onRemindersSwitched(CompoundButton button, boolean checked) {
        mPresenter.onRemindersSwitched(checked);
    }


    @Override
    public void showProgress(boolean show) {
        if (show) {
            mContainer.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            return;
        }
        mContainer.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
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
    public void setHandoverReminder(String s) {
        mHandover.setText(s);
    }

    @Override
    public void setReturnReminder(String s) {
        mReturn.setText(s);
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

package com.cardee.renter_profile.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cardee.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RenterProfileActivity extends AppCompatActivity implements RenterProfileView {


    private Toast mCurrentToast;

    @BindView(R.id.renter_profile_container)
    View mContainer;

    @BindView(R.id.renter_profile_progress)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_profile);
        ButterKnife.bind(this);

        initToolBar();
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
    public void setProfileName(String name) {

    }

    @Override
    public void setProfileRating(String rating) {

    }

    @Override
    public void setProfileAge(String age) {

    }

    @Override
    public void setDrivingExp(String exp) {

    }

    @Override
    public void setBookings(String count) {

    }

    @Override
    public void setProfileImage(String photoLink) {

    }

    @Override
    public void setNote(String note) {

    }

    @Override
    public void setReviewsCount(String text) {

    }

    @Override
    public void onChangeImageSuccess() {

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

package com.cardee.owner_bookings.car_returned.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cardee.R;
import com.cardee.custom.CustomRatingBar;
import com.cardee.owner_bookings.car_returned.presenter.CarReturnedPresenter;
import com.cardee.owner_home.view.OwnerHomeActivity;
import com.cardee.util.glide.CircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarReturnedActivity extends AppCompatActivity implements CarReturnedView {

    private CarReturnedPresenter mPresenter;
    private Toast mCurrentToast;
    private int mBookingId;

    @BindView(R.id.car_title)
    TextView mCarTitle;

    @BindView(R.id.car_number)
    TextView mCarNumber;

    @BindView(R.id.current_date)
    TextView mCurrentDate;

    @BindView(R.id.rental_period)
    TextView mRentalPeriod;

    @BindView(R.id.car_photo)
    ImageView mCarPhoto;

    @BindView(R.id.renter_photo)
    ImageView mRenterPhoto;

    @BindView(R.id.rate_text)
    TextView mRenterName;

    @BindView(R.id.b_submit)
    TextView mSubmit;

    @BindView(R.id.et_comment)
    AppCompatEditText mEditText;

    @BindView(R.id.rating_bar)
    CustomRatingBar mRatingBar;

    @BindView(R.id.progress)
    ProgressBar mProgressBar;

    @BindView(R.id.container)
    View mContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_returned);
        ButterKnife.bind(this);

        initToolbar();
        mPresenter = new CarReturnedPresenter(this);
        getIntentData();
        mPresenter.getData(mBookingId);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            mBookingId = intent.getIntExtra("booking_id", -1);
        }
    }

    @Override
    public void setCommentHint(String hint) {
        mEditText.setHint(hint);
    }

    @Override
    public void setRenterName(String renterName) {
        mRenterName.setText(renterName);
    }

    @Override
    public void setRenterPhoto(String renterPhoto) {
        Glide.with(this)
                .load(renterPhoto)
                .placeholder(getResources().getDrawable(R.drawable.ic_photo_placeholder))
                .error(getResources().getDrawable(R.drawable.ic_photo_placeholder))
                .centerCrop()
                .transform(new CircleTransform(this))
                .into(mRenterPhoto);
    }

    @Override
    public void setCarPhoto(String link) {
        Glide.with(this)
                .load(link)
                .placeholder(getResources().getDrawable(R.drawable.img_no_car))
                .error(getResources().getDrawable(R.drawable.img_no_car))
                .centerCrop()
                .into(mCarPhoto);
    }

    @Override
    public void setBookingDate(String createdDate) {
        mCurrentDate.setText(createdDate);
    }

    @Override
    public void setRentalPeriod(String period) {
        mRentalPeriod.setText(period);
    }

    @Override
    public void setCarNumber(String number) {
        mCarNumber.setText(number);
    }

    @OnClick(R.id.b_submit)
    public void onSubmitClicked() {
        String comment = mEditText.getText().toString();
        if (comment == null || comment.isEmpty()) {
            comment = "";
        }
        byte rate = (byte) mRatingBar.getScore();

        if (rate != 0) {
            mPresenter.omSubmitClicked(comment, rate, mBookingId);
        }
    }

    @Override
    public void setCarTitle(Spannable text) {
        mCarTitle.setText(text);
    }

    @Override
    public void onSendCommentSuccess() {
        showMessage(R.string.rental_exp_review_sent);
        finish();
        Intent homeIntent = new Intent(this, OwnerHomeActivity.class);
        startActivity(homeIntent);
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
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

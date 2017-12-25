package com.cardee.owner_bookings.car_returned.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
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
import com.cardee.util.glide.CircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarReturnedActivity extends AppCompatActivity implements CarReturnedView {

    private CarReturnedPresenter mPresenter;
    private Toast mCurrentToast;

    private String carName;
    private String carYear;
    private String carNumber;
    private String currentDate;
    private String rentalPeriod;
    private String carPhoto;
    private String renterPhoto;
    private String renterName;
    private int mBookindId;

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
    AppCompatButton mSubmit;

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

        mPresenter = new CarReturnedPresenter(this);
        getData();
        setFields();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            mBookindId = intent.getIntExtra("booking_id", -1);
            carName = intent.getStringExtra("car_title");
            carYear = intent.getStringExtra("car_year");
            carNumber = intent.getStringExtra("car_number");
            currentDate = intent.getStringExtra("current_date");
            rentalPeriod = intent.getStringExtra("rental_period");
            carPhoto = intent.getStringExtra("car_photo_link");
            renterPhoto = intent.getStringExtra("renter_photo_link");
            renterName = intent.getStringExtra("renter_name");
        }
    }

    private void setFields() {
        mPresenter.setCarTitle(carName, carYear);
        setCarNumber();
        setCurrentDate();
        setRentalPeriod();
        setCarPhoto();
        setRenterPhoto();
        setRenterName();
    }

    private void setRenterName() {
        mRenterName.setText(String.format("%s%s", getResources().getString(R.string.car_returned_rate), renterName));
    }

    private void setRenterPhoto() {
        Glide.with(this)
                .load(renterPhoto)
                .placeholder(getResources().getDrawable(R.drawable.ic_photo_placeholder))
                .error(getResources().getDrawable(R.drawable.ic_photo_placeholder))
                .centerCrop()
                .transform(new CircleTransform(this))
                .into(mRenterPhoto);
    }

    private void setCarPhoto() {
        Glide.with(this)
                .load(carPhoto)
                .placeholder(getResources().getDrawable(R.drawable.img_no_car))
                .error(getResources().getDrawable(R.drawable.img_no_car))
                .centerCrop()
                .into(mCarPhoto);
    }

    private void setRentalPeriod() {
        mRentalPeriod.setText(rentalPeriod);
    }

    private void setCurrentDate() {
        mCurrentDate.setText(currentDate);
    }

    private void setCarNumber() {
        mCarNumber.setText(carNumber);
    }

    @OnClick(R.id.b_submit)
    public void onSubmitClicked() {
//        String comment = mEditText.getText().toString();
//        if (comment != null && !comment.isEmpty()) {
//            byte rate = (byte) mRatingBar.getScore();
//            mPresenter.omSubmitClicked(comment, rate, mBookindId);
//        }

        showMessage(String.valueOf(mRatingBar.getScore()));
    }

    @Override
    public void setCarTitle(Spannable text) {
        mCarTitle.setText(text);
    }

    @Override
    public void onSendCommentSuccess() {
        showMessage(R.string.send_comment_success);
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

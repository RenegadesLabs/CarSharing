package com.cardee.renter_bookings.rate_rental_exp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cardee.R;
import com.cardee.owner_profile_info.view.OwnerProfileInfoActivity;
import com.cardee.renter_bookings.rate_rental_exp.presenter.RateRentalExpPresenter;
import com.cardee.util.glide.CircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;

public class RateRentalExpActivity extends AppCompatActivity implements RateRentalExpView,
        FirstRateFragment.OnFirstFragmentClickListener, SecondRateFragment.OnSecondFragmentClickedListener {

    private RateRentalExpPresenter mPresenter;
    private boolean mHasFragment;
    private Toast mCurrentToast;
    private FragmentManager fragmentManager;
    private int mBookingId;

    @BindView(R.id.car_photo)
    ImageView mCarPhoto;

    @BindView(R.id.owner_photo)
    ImageView mOwnerPhoto;

    @BindView(R.id.car_title)
    TextView mCarTitle;

    @BindView(R.id.rental_period)
    TextView mRentalPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_rental_exp);
        ButterKnife.bind(this);

        initToolBar();
        getIntentData();
        mPresenter = new RateRentalExpPresenter(this, mBookingId);
        mPresenter.getBookingData();
        fragmentManager = getSupportFragmentManager();
        showFirstFragment();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mBookingId = intent.getIntExtra("booking_id", -1);
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    @OnClick(R.id.owner_photo)
    public void onOwnerPhotoClicked() {
        Intent intent = new Intent(this, OwnerProfileInfoActivity.class);
        intent.putExtra("editable", false);
        intent.putExtra("profile_id", mPresenter.getProfileId());
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        mHasFragment = true;
    }

    @Override
    public void onFirstSubmitClicked(byte condition, byte comfort, byte owner, byte overall) {
        mPresenter.setRates(condition, comfort, owner, overall);
        showSecondFragment();
    }

    @Override
    public void onSecondSubmitClicked(String review) {
        mPresenter.sendRateAndReview(review);
    }

    @Override
    public void onEditRateClicked() {
        onBackPressed();
    }

    @Override
    public void setCarTitle(Spannable text) {
        mCarTitle.setText(text);
    }

    @Override
    public void setRentalPeriod(String period) {
        mRentalPeriod.setText(period);
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
    public void setOwnerPhoto(String ownerPhoto) {
        Glide.with(this)
                .load(ownerPhoto)
                .placeholder(getResources().getDrawable(R.drawable.ic_photo_placeholder))
                .error(getResources().getDrawable(R.drawable.ic_photo_placeholder))
                .centerCrop()
                .transform(new CircleTransform(this))
                .into(mOwnerPhoto);
    }

    private void showFirstFragment() {
        Fragment fragment = fragmentManager.findFragmentByTag(FirstRateFragment.class.getName());
        if (fragment == null) {

            fragment = FirstRateFragment.newInstance(this);
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (mHasFragment) {
            transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        } else {
            transaction.add(R.id.fragment_container, fragment, fragment.getClass().getName());
        }
        transaction.setTransition(TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    private void showSecondFragment() {
        Fragment fragment = fragmentManager.findFragmentByTag(SecondRateFragment.class.getName());
        if (fragment == null) {
            fragment = mPresenter.getSecondFragment(this);

        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (mHasFragment) {
            transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        } else {
            transaction.add(R.id.fragment_container, fragment, fragment.getClass().getName());
        }
        transaction.addToBackStack(null);
        transaction.setTransition(TRANSIT_FRAGMENT_FADE);
        transaction.commit();
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onSendRateSuccess() {
        showMessage(getString(R.string.rental_exp_review_sent));
        finish();
    }


}

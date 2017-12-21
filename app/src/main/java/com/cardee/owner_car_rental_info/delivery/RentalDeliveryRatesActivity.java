package com.cardee.owner_car_rental_info.delivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.data_source.util.DialogHelper;
import com.cardee.owner_car_details.view.OwnerCarRentalFragment;
import com.cardee.owner_car_rental_info.RentalContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RentalDeliveryRatesActivity extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener, RentalContract.View {

    public final static String BASE_RATE = "key_delivery_base_rate";
    public final static String DISTANCE_RATE = "key_delivery_distance_rate";
    public final static String PROVIDE_FREE = "key_delivery_provide_free";
    public final static String RENTAL_DURATION = "key_delivery_rental_duration";

    private final static String TAG = RentalDeliveryRatesActivity.class.getSimpleName();

    @BindView(R.id.et_ratesValue1)
    public AppCompatEditText baseRateET;

    @BindView(R.id.et_ratesValue2)
    public AppCompatEditText distanceRateET;

    @BindView(R.id.sw_ratesDeliveryFree)
    public SwitchCompat freeDeliverySW;

    @BindView(R.id.et_ratesValue5)
    public AppCompatEditText rentalDurationET;

    @BindView(R.id.tv_rentalValue5)
    public TextView freeForValueTW;

    @BindView(R.id.cl_ratesFreeDeliveryContainer)
    public View freeForContainer;

    private float mBaseRate;

    private float mDistanceRate;

    private boolean isFreeDelivery;

    private float mRentalDuration;

    private ProgressDialog mProgress;

    private RentalDeliveryRatesPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_rental_delivery_rates);
        ButterKnife.bind(this);
        initToolbar();
        getIntentData();
        initViews();
        mProgress = DialogHelper.getProgressDialog(this,
                getString(R.string.loading), false);
        mPresenter = new RentalDeliveryRatesPresenter(this);
    }

    private void getIntentData() {
        Intent i = getIntent();
        mBaseRate = i.getFloatExtra(BASE_RATE, .0f);
        mDistanceRate = i.getFloatExtra(DISTANCE_RATE, .0f);
        isFreeDelivery = i.getBooleanExtra(PROVIDE_FREE, false);
        mRentalDuration = i.getFloatExtra(RENTAL_DURATION, .0f);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) {
            return;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void initViews() {
        baseRateET.setText(String.valueOf(mBaseRate));
        distanceRateET.setText(String.valueOf(mDistanceRate));
        freeDeliverySW.setOnCheckedChangeListener(this);
        freeDeliverySW.setChecked(isFreeDelivery);
        rentalDurationET.setText(String.valueOf(Math.round(mRentalDuration)));
        freeForValueTW.setText(Math.round(mRentalDuration) == 1 ? "day" : "days");
    }


    private void setDeliveryFreeViewState(boolean show) {
        if (show) {
            freeForContainer.setVisibility(View.VISIBLE);
            return;
        }
        freeForContainer.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.toolbar_action)
    public void onSaveClicked() {
        String baseRate = baseRateET.getText().toString();
        String distanceRate = distanceRateET.getText().toString();
        String rentalDuration = rentalDurationET.getText().toString();
        if (baseRate.isEmpty() || distanceRate.isEmpty()) {
            showMessage(R.string.error_empty_field);
            return;
        }
        mPresenter.save(Float.parseFloat(baseRate), Float.parseFloat(distanceRate),
                freeDeliverySW.isChecked(), Float.parseFloat(rentalDuration));
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.sw_ratesDeliveryFree:
                setDeliveryFreeViewState(b);
                break;
        }
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            mProgress.show();
            return;
        }
        mProgress.dismiss();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        onBackPressed();
        finish();
    }
}

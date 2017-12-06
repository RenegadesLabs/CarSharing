package com.cardee.owner_car_rental_info.fuel;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.owner_car_details.view.OwnerCarRentalFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RentalFuelPolicyActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_fuelSimilarLevel)
    public CheckedTextView similarTV;

    @BindView(R.id.tv_fuelMileage)
    public CheckedTextView mileageTV;

    @BindView(R.id.iv_fuelSimilar)
    public AppCompatImageView similarIV;

    @BindView(R.id.iv_fuelMileage)
    public AppCompatImageView mileageIV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_rental_fuel);
        ButterKnife.bind(this);
        int mode = getIntent().getIntExtra(OwnerCarRentalFragment.MODE, 0);
        initToolbar(mode);
        initViewsState(mode);
    }

    private void initToolbar(int mode) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView titleTV = toolbar.findViewById(R.id.toolbar_title);
        String title = getString(R.string.car_rental_info_fuel);
        switch (mode) {
            case OwnerCarRentalFragment.HOULY:
                title += " " + getString(R.string.car_rental_info_hourly_b);
                break;
            case OwnerCarRentalFragment.DAILY:
                title += " " + getString(R.string.car_rental_info_daily_b);
                break;
        }
        titleTV.setText(title);
        toolbar.findViewById(R.id.toolbar_action).setOnClickListener(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) {
            return;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void initViewsState(int mode) {
        if (mode == OwnerCarRentalFragment.HOULY) {
            findViewById(R.id.ll_fuelMileageContainer)
                    .setVisibility(View.VISIBLE);
            return;
        }
        findViewById(R.id.ll_fuelMileageContainer)
                .setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_fuelSimilarLevel)
    public void onReturnWithSimilarLevelClicked() {
        toggleTV(similarTV, mileageTV);
        toggleIV(similarIV, mileageIV);
    }

    @OnClick(R.id.tv_fuelMileage)
    public void onPaymentByMileageClicked() {
        toggleTV(mileageTV, similarTV);
        toggleIV(mileageIV, similarIV);
    }

    @OnClick(R.id.tv_fuelPerKm)
    public void onFuelPerKmClicked() {

    }

    private void toggleTV(CheckedTextView tv1, CheckedTextView tv2) {
        if (!tv1.isChecked()) {
            tv1.setChecked(true);
            tv2.setChecked(false);
            tv1.setTextColor(Color.BLACK);
            tv2.setTextColor(getResources().getColor(R.color.text_disabled));
        }
    }

    private void toggleIV(AppCompatImageView iv1, AppCompatImageView iv2) {
        iv1.setImageResource(R.drawable.ic_check);
        iv2.setImageResource(-1);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_action:
                // TODO: 12/4/17 Save
                break;
        }

    }
}

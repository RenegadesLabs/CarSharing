package com.cardee.owner_car_rental_info.fuel;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.cardee.R;
import com.cardee.custom.modal.PickerFuelMenuFragment;
import com.cardee.data_source.util.DialogHelper;
import com.cardee.owner_car_details.view.OwnerCarRentalFragment;
import com.cardee.owner_car_rental_info.RentalContract;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RentalFuelPolicyActivity extends AppCompatActivity implements View.OnClickListener,
        RentalContract.View {

    @BindView(R.id.tv_fuelSimilarLevel)
    public CheckedTextView similarTV;

    @BindView(R.id.tv_fuelMileage)
    public CheckedTextView mileageTV;

    @BindView(R.id.iv_fuelSimilar)
    public AppCompatImageView similarIV;

    @BindView(R.id.iv_fuelMileage)
    public AppCompatImageView mileageIV;

    @BindView(R.id.tv_fuelPerKm)
    public TextView costValue;

    @BindView(R.id.tv_fuelConsumptionText)
    public TextView consumptionValue;

    @BindView(R.id.fl_fuelCostTextContainer)
    public View mileageSettingContainer;

    private String mPickerSelectedVal;

    private int mMode;

    private ProgressDialog mProgress;

    private RentalFuelPolicyPresenter mPresenter;

    private int mFuelPolicyId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_rental_fuel);
        ButterKnife.bind(this);
        mMode = getIntent().getIntExtra(OwnerCarRentalFragment.MODE, 0);
        mPresenter = new RentalFuelPolicyPresenter(this);
        mProgress = DialogHelper.getProgressDialog(this, getString(R.string.loading), false);
        initToolbar();
        initViewsState();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView titleTV = toolbar.findViewById(R.id.toolbar_title);
        String title = getString(R.string.car_rental_info_fuel);
        switch (mMode) {
            case OwnerCarRentalFragment.HOURLY:
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

    private void initViewsState() {

        if (mMode == OwnerCarRentalFragment.HOURLY) {
            findViewById(R.id.ll_fuelMileageContainer)
                    .setVisibility(View.VISIBLE);
            mPickerSelectedVal = "$0.16";
            mileageSettingContainer.setVisibility(View.GONE);
            consumptionValue.setVisibility(View.GONE);
            return;
        }
        findViewById(R.id.ll_fuelMileageContainer)
                .setVisibility(View.GONE);
    }

    @OnClick(R.id.fl_fuelSimilarContainer)
    public void onReturnWithSimilarLevelClicked() {
        mFuelPolicyId = 0;
        toggleTV(similarTV, mileageTV, similarIV, mileageIV);
    }

    @OnClick(R.id.fl_fuelMileageContainer)
    public void onPaymentByMileageClicked() {
        mFuelPolicyId = 1;
        toggleTV(mileageTV, similarTV, mileageIV, similarIV);
    }

    @OnClick(R.id.tv_fuelPerKm)
    public void onFuelPerKmClicked() {
        PickerFuelMenuFragment fm = PickerFuelMenuFragment.getInstance(mPickerSelectedVal);
        fm.show(getSupportFragmentManager(), fm.getTag());
        fm.setOnDoneClickListener(new PickerFuelMenuFragment.DialogOnClickListener() {
            @Override
            public void onDoneClicked(String value1, String value2) {
                mPickerSelectedVal = value1;
                costValue.setText(value1);
                String consumptionTxt = "Suitable for car average petrol consumption of " + value2;
                consumptionValue.setText(consumptionTxt);
            }
        });
    }

    private void toggleTV(CheckedTextView tv1, CheckedTextView tv2,
                          AppCompatImageView iv1, AppCompatImageView iv2) {
        if (!tv1.isChecked()) {
            tv1.setChecked(true);
            tv2.setChecked(false);
            tv1.setTextColor(Color.BLACK);
            tv2.setTextColor(getResources().getColor(R.color.text_disabled));
            iv1.setImageResource(R.drawable.ic_check);
            iv2.setImageResource(android.R.color.transparent);

        } else {
            tv1.setChecked(false);
            tv1.setTextColor(getResources().getColor(R.color.text_disabled));
            iv1.setImageResource(android.R.color.transparent);
            mFuelPolicyId = -1;
        }
        toggleMileage(mileageTV);
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

    private void toggleMileage(CheckedTextView tv) {
        if (tv.isChecked()) {
            mileageSettingContainer.setVisibility(View.VISIBLE);
            consumptionValue.setVisibility(View.VISIBLE);
            return;
        }
        mileageSettingContainer.setVisibility(View.GONE);
        consumptionValue.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_action:
                if (mFuelPolicyId == -1) {
                    showMessage(R.string.nothing_to_save);
                    break;
                }

                if (mMode == OwnerCarRentalFragment.HOURLY) {
                    mPresenter.save(mMode, mFuelPolicyId,
                            Float.parseFloat(mPickerSelectedVal.replace("$", "")));
                    break;
                }
                mPresenter.save(mMode, mFuelPolicyId);
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

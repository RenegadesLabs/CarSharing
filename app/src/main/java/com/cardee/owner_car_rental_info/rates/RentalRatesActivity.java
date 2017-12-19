package com.cardee.owner_car_rental_info.rates;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.data_source.util.DialogHelper;
import com.cardee.owner_car_details.view.OwnerCarRentalFragment;
import com.cardee.owner_car_rental_info.RentalContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RentalRatesActivity extends AppCompatActivity implements RentalContract.View {

    public final static String RATE_FIRST = "key_rental_rate_first";
    public final static String RATE_SECOND = "key_rental_rate_second";
    public final static String DISCOUNT_FIRST = "key_rental_discount_first";
    public final static String DISCOUNT_SECOND = "key_rental_discount_second";
    public final static String MIN_RENTAL = "key_rental_minimum";

    @BindView(R.id.tv_ratesTitle)
    public TextView ratesTitleTV;

    @BindView(R.id.tv_ratesPeakDesc)
    public TextView ratesPeakDesc;

    @BindView(R.id.tv_ratesTitleValue1)
    public TextView ratesValueName1;

    @BindView(R.id.tv_ratesTitleValue2)
    public TextView ratesValueName2;

    @BindView(R.id.tv_ratesTitleValue3)
    public TextView ratesValueName3;

    @BindView(R.id.tv_ratesTitleValue4)
    public TextView ratesValueName4;

    @BindView(R.id.tv_ratesTitleValue5)
    public TextView ratesValueName5;

    @BindView(R.id.tv_ratesValue1Text)
    public TextView ratesTextValue1;

    @BindView(R.id.tv_ratesValue2Text)
    public TextView ratesTextValue2;

    @BindView(R.id.tv_rentalValue5)
    public TextView ratesTextValue5;

    @BindView(R.id.et_ratesValue1)
    public AppCompatEditText ratesValue1ET;

    @BindView(R.id.et_ratesValue2)
    public AppCompatEditText ratesValue2ET;

    @BindView(R.id.et_ratesValue3)
    public AppCompatEditText ratesValue3ET;

    @BindView(R.id.et_ratesValue4)
    public AppCompatEditText ratesValue4ET;

    @BindView(R.id.et_ratesValue5)
    public AppCompatEditText ratesValue5ET;

    @BindView(R.id.cl_ratesValue1Container)
    public ConstraintLayout item1Container;

    private ProgressDialog mProgress;

    private RentalRatesPresenter mPresenter;

    private int mMode;
    private String mRateFirst;
    private String mRateSecond;
    private String mDiscountFirst;
    private String mDiscountSecond;
    private int mMinRental;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_rental_rates);
        ButterKnife.bind(this);
        getExtras();
        initToolbar();
        initViews();
        mPresenter = new RentalRatesPresenter(this);
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

    private void getExtras() {
        Intent i = getIntent();
        mMode = i.getIntExtra(OwnerCarRentalFragment.MODE, 0);
        mRateFirst = i.getStringExtra(RATE_FIRST);
        mRateSecond = i.getStringExtra(RATE_SECOND);
        mDiscountFirst = i.getStringExtra(DISCOUNT_FIRST);
        mDiscountSecond = i.getStringExtra(DISCOUNT_SECOND);
        mMinRental = i.getIntExtra(MIN_RENTAL, 0);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView titleTV = toolbar.findViewById(R.id.toolbar_title);
        String title = getString(R.string.car_rental_info_rates);
        switch (mMode) {
            case OwnerCarRentalFragment.HOURLY:
                title += " " + getString(R.string.car_rental_info_hourly_b);
                break;
            case OwnerCarRentalFragment.DAILY:
                title += " " + getString(R.string.car_rental_info_daily_b);
                break;
        }
        titleTV.setText(title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) {
            return;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void initViews() {
        mProgress = DialogHelper.getProgressDialog(this,
                getString(R.string.loading), false);
        ratesValue1ET.setText(mRateFirst);
        ratesValue2ET.setText(mRateSecond);
        ratesValue3ET.setText(mDiscountFirst);
        ratesValue4ET.setText(mDiscountSecond);
        ratesValue5ET.setText(String.valueOf(mMinRental));
        switch (mMode) {
            case OwnerCarRentalFragment.HOURLY:
                ratesTitleTV.setText(R.string.car_rental_rates_hourly);
                ratesValueName1.setText(R.string.car_rental_rates_peak_h);
                ratesTextValue1.setText(R.string.car_rental_rates_per_hour);
                ratesTextValue2.setText(R.string.car_rental_rates_per_hour);
                ratesPeakDesc.setText(R.string.car_rental_rates_peak_desc);
                ratesValueName2.setText(R.string.car_rental_rates_peak_non_h);
                ratesValueName3.setText(R.string.car_rental_rates_discount_4_hours);
                ratesValueName4.setText(R.string.car_rental_rates_discount_8_hours);
                if (ratesValue5ET.getText().toString().equals("1")) {
                    ratesTextValue5.setText(R.string.hour);
                    return;
                }
                ratesTextValue5.setText(R.string.hours);
                break;
            case OwnerCarRentalFragment.DAILY:
                ratesTitleTV.setText(R.string.car_rental_rates_daily);
                ratesValueName1.setText(R.string.car_rental_rates_weekdays);
                ratesTextValue1.setText(R.string.car_rental_rates_per_day);
                ratesTextValue2.setText(R.string.car_rental_rates_per_day);
                ratesValueName2.setText(R.string.car_rental_rates_weekends);
                ratesValueName3.setText(R.string.car_rental_rates_discount_3_days);
                ratesValueName4.setText(R.string.car_rental_rates_discount_weekly);
                if (ratesValue5ET.getText().toString().equals("1")) {
                    ratesTextValue5.setText(R.string.day);
                    return;
                }
                ratesTextValue5.setText(R.string.days);
                break;
        }
    }

    @OnClick(R.id.toolbar_action)
    public void onSaveClicked() {
        String firstRate = ratesValue1ET.getText().toString();
        String secondRate = ratesValue2ET.getText().toString();
        String firstDiscount = ratesValue3ET.getText().toString();
        String secondDiscount = ratesValue4ET.getText().toString();
        String minRental = ratesValue5ET.getText().toString();

        if (firstRate.isEmpty() || secondRate.isEmpty()
                || firstDiscount.isEmpty() || secondDiscount.isEmpty()
                || minRental.isEmpty()) {
            showMessage(R.string.error_empty_field);
            return;
        }
        mPresenter.save(firstRate, secondRate, firstDiscount, secondDiscount, minRental, mMode);
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

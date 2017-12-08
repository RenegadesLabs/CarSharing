package com.cardee.owner_car_details.view.viewholder;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.domain.owner.entity.RentalDetails;
import com.cardee.mvp.BaseView;
import com.cardee.owner_car_details.view.OwnerCarRentalFragment;
import com.cardee.owner_car_details.view.listener.ChildProgressListener;
import com.cardee.owner_car_rental_info.fuel.RentalFuelPolicyActivity;
import com.cardee.owner_car_rental_info.terms.view.RentalTermsActivity;

public class HourlyRentalViewHolder extends BaseViewHolder<RentalDetails>
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, BaseView {

    private RentalDetails hourlyRental;

    private TextView availabilityDays;
    private TextView timing;
    private View availabilityDaysEdit;
    private View timeEdit;
    private View instantBookingTitle;
    private AppCompatImageView instantBookingIcon;
    private Switch instantBookingSwitch;
    private TextView instantBookingEdit;
    private View settingsHelp;
    private View curbsideDeliveryTitle;
    private AppCompatImageView curbsideDeliveryIcon;
    private Switch curbsideDeliverySwitch;
    private View curbsideDeliveryEdit;
    private View acceptCashTitle;
    private AppCompatImageView acceptCashImage;
    private Switch acceptCashSwitch;
    private View rentalRatesEdit;
    private TextView rentalRatesValue;
    private View fuelPolicyEdit;
    private TextView fuelPolicyValue;
    private View rentalTermsEdit;

    private ChildProgressListener progressListener;
    private Toast currentToast;


    public HourlyRentalViewHolder(@NonNull View rootView, @NonNull Activity activity) {
        super(rootView, activity);
        availabilityDays = rootView.findViewById(R.id.availability_days);
        timing = rootView.findViewById(R.id.tv_rentalAvailableTimingValue);
        availabilityDaysEdit = rootView.findViewById(R.id.tv_rentalAvailabilityEdit);
        timeEdit = rootView.findViewById(R.id.tv_rentalTimingEdit);
        settingsHelp = rootView.findViewById(R.id.iv_rentalHelp);
        instantBookingTitle = rootView.findViewById(R.id.instant_booking);
        instantBookingIcon = rootView.findViewById(R.id.icon_instant);
        instantBookingSwitch = rootView.findViewById(R.id.sw_rentalInstant);
        instantBookingEdit = rootView.findViewById(R.id.tv_rentalInstantEdit);
        curbsideDeliveryTitle = rootView.findViewById(R.id.curbside_delivery);
        curbsideDeliveryIcon = rootView.findViewById(R.id.icon_curbside);
        curbsideDeliverySwitch = rootView.findViewById(R.id.sw_rentalDelivery);
        curbsideDeliveryEdit = rootView.findViewById(R.id.tv_rentalCurbsideRatesEdit);
        acceptCashImage = rootView.findViewById(R.id.icon_cash);
        acceptCashTitle = rootView.findViewById(R.id.accept_cash);
        acceptCashSwitch = rootView.findViewById(R.id.sw_rentalCash);
        rentalRatesEdit = rootView.findViewById(R.id.tv_rentalRentalRatesEdit);
        rentalRatesValue = rootView.findViewById(R.id.tv_rentalOffPeakValue);
        fuelPolicyEdit = rootView.findViewById(R.id.tv_rentalFuelEdit);
        fuelPolicyValue = rootView.findViewById(R.id.tv_rentalFuelValue);
        rentalTermsEdit = rootView.findViewById(R.id.cl_rentalTermsContainer);
        availabilityDaysEdit.setOnClickListener(this);
        timeEdit.setOnClickListener(this);
        instantBookingEdit.setOnClickListener(this);
        curbsideDeliveryEdit.setOnClickListener(this);
        rentalRatesEdit.setOnClickListener(this);
        fuelPolicyEdit.setOnClickListener(this);
        rentalTermsEdit.setOnClickListener(this);
        settingsHelp.setOnClickListener(this);
        instantBookingSwitch.setOnCheckedChangeListener(this);
        curbsideDeliverySwitch.setOnCheckedChangeListener(this);
        acceptCashSwitch.setOnCheckedChangeListener(this);
        initResources();
    }

    private void initResources() {

    }

    @Override
    public void bind(RentalDetails model) {
        hourlyRental = model;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cl_rentalTermsContainer:
                getActivity().startActivity(new Intent(getActivity(),
                        RentalTermsActivity.class));
                break;
            case R.id.tv_rentalFuelEdit:
                Intent i = new Intent(getActivity(),
                        RentalFuelPolicyActivity.class);
                i.putExtra(OwnerCarRentalFragment.MODE, OwnerCarRentalFragment.HOURLY);
                getActivity().startActivity(i);
                break;
            case R.id.tv_rentalAvailabilityEdit:
            case R.id.tv_rentalTimingEdit:
            case R.id.tv_rentalInstantEdit:
            case R.id.tv_rentalCurbsideRatesEdit:
            case R.id.tv_rentalRentalRatesEdit:
            case R.id.iv_rentalHelp:
                showMessage("Coming soon");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.sw_rentalInstant:
            case R.id.sw_rentalDelivery:
            case R.id.sw_rentalCash:
                showMessage("Checked: " + b);
        }
    }

    @Override
    public void showProgress(boolean show) {
        if (progressListener != null) {
            progressListener.onChildProgressShow(show);
        }
    }

    @Override
    public void showMessage(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        currentToast.show();
    }

    @Override
    public void showMessage(@StringRes int messageId) {
        showMessage(getActivity().getString(messageId));
    }

    public void setProgressListener(ChildProgressListener progressListener) {
        this.progressListener = progressListener;
    }
}

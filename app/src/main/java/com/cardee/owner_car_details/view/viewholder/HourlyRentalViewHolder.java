package com.cardee.owner_car_details.view.viewholder;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.domain.owner.entity.RentalDetails;
import com.cardee.owner_car_details.AvailabilityContract;
import com.cardee.owner_car_details.RentalDetailsContract;
import com.cardee.owner_car_details.presenter.StrategyRentalDetailPresenter;
import com.cardee.owner_car_details.view.AvailabilityCalendarActivity;
import com.cardee.owner_car_details.view.OwnerCarRentalFragment;
import com.cardee.owner_car_details.view.listener.ChildProgressListener;
import com.cardee.owner_car_details.view.service.AvailabilityStringDelegate;
import com.cardee.owner_car_rental_info.fuel.RentalFuelPolicyActivity;
import com.cardee.owner_car_rental_info.terms.view.RentalTermsActivity;

public class HourlyRentalViewHolder extends BaseViewHolder<RentalDetails>
        implements View.OnClickListener, RentalDetailsContract.ControlView {

    private TextView availabilityDays;
    private TextView timing;
    private View availabilityDaysEdit;
    private View timeEdit;
    private View instantBookingTitle;
    private AppCompatImageView instantBookingIcon;
    private SwitchCompat instantBookingSwitch;
    private TextView instantBookingEdit;
    private View settingsHelp;
    private View curbsideDeliveryTitle;
    private AppCompatImageView curbsideDeliveryIcon;
    private SwitchCompat curbsideDeliverySwitch;
    private View curbsideDeliveryEdit;
    private View acceptCashTitle;
    private AppCompatImageView acceptCashImage;
    private SwitchCompat acceptCashSwitch;
    private View rentalRatesEdit;
    private TextView rentalRatesValue;
    private View fuelPolicyEdit;
    private TextView fuelPolicyValue;
    private View rentalTermsEdit;

    private RentalDetails hourlyRental;
    private AvailabilityStringDelegate stringDelegate;
    private StrategyRentalDetailPresenter presenter;
    private ChildProgressListener progressListener;
    private Toast currentToast;


    public HourlyRentalViewHolder(@NonNull View rootView, @NonNull AppCompatActivity activity) {
        super(rootView, activity);
        presenter = new StrategyRentalDetailPresenter(this, StrategyRentalDetailPresenter.Strategy.HOURLY);
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
        instantBookingSwitch.setOnCheckedChangeListener(presenter);
        curbsideDeliverySwitch.setOnCheckedChangeListener(presenter);
        acceptCashSwitch.setOnCheckedChangeListener(presenter);
        initResources(activity);
    }

    private void initResources(Context context) {
        stringDelegate = new AvailabilityStringDelegate(context);
    }

    @Override
    public void bind(RentalDetails model) {
        hourlyRental = model;
        presenter.onBind(model);
    }

    @Override
    public void onClick(View view) {
        if (hourlyRental == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.cl_rentalTermsContainer:
                getActivity().startActivity(new Intent(getActivity(), RentalTermsActivity.class));
                break;
            case R.id.tv_rentalFuelEdit:
                Intent i = new Intent(getActivity(), RentalFuelPolicyActivity.class);
                i.putExtra(OwnerCarRentalFragment.MODE, OwnerCarRentalFragment.HOURLY);
                getActivity().startActivity(i);
                break;
            case R.id.tv_rentalAvailabilityEdit:
                Intent intent = new Intent(getActivity(), AvailabilityCalendarActivity.class);
                Bundle args = new Bundle();
                args.putInt(AvailabilityContract.CAR_ID, hourlyRental.getCarId());
                args.putSerializable(AvailabilityContract.CALENDAR_MODE, AvailabilityContract.Mode.HOURLY);
                intent.putExtras(args);
                getActivity().startActivity(intent);
                break;
            case R.id.tv_rentalTimingEdit:
            case R.id.tv_rentalInstantEdit:
            case R.id.tv_rentalCurbsideRatesEdit:
            case R.id.tv_rentalRentalRatesEdit:
            case R.id.iv_rentalHelp:
                showMessage("Coming soon");
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

    @Override
    public void setData(RentalDetails rentalDetails) {
        stringDelegate.onSetValue(availabilityDays, rentalDetails.getHourlyCount());
    }

    @Override
    public void onInstantEnabled(boolean enabled) {

    }

    @Override
    public void onCurbsideEnabled(boolean enabled) {

    }

    @Override
    public void onCashEnabled(boolean enabled) {

    }
}

package com.cardee.owner_car_details.view.viewholder;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.custom.modal.BookingPickerMenuFragment;
import com.cardee.custom.modal.PickerMenuFragment;
import com.cardee.custom.modal.DailyAvailabilityTimingFragment;
import com.cardee.domain.owner.entity.RentalDetails;
import com.cardee.owner_car_details.AvailabilityContract;
import com.cardee.owner_car_details.RentalDetailsContract;
import com.cardee.owner_car_details.presenter.StrategyRentalDetailPresenter;
import com.cardee.owner_car_details.view.AvailabilityCalendarActivity;
import com.cardee.owner_car_details.view.OwnerCarRentalFragment;
import com.cardee.owner_car_details.view.eventbus.DailyTimingEventBus;
import com.cardee.owner_car_details.view.eventbus.TimingSaveEvent;
import com.cardee.owner_car_details.view.listener.ChildProgressListener;
import com.cardee.util.DateStringDelegate;
import com.cardee.owner_car_rental_info.fuel.RentalFuelPolicyActivity;
import com.cardee.owner_car_rental_info.rates.RentalRatesActivity;
import com.cardee.owner_car_rental_info.terms.view.RentalTermsActivity;


public class DailyRentalViewHolder extends BaseViewHolder<RentalDetails>
        implements View.OnClickListener, RentalDetailsContract.ControlView,
        DailyTimingEventBus.Listener, CompoundButton.OnCheckedChangeListener {

    private TextView availabilityDays;
    private TextView timingPickup;
    private TextView timingReturn;
    private View availabilityDaysEdit;
    private View timeEdit;
    private View settingsHelp;
    private TextView instantBookingTitle;
    private AppCompatImageView instantBookingIcon;
    private SwitchCompat instantBookingSwitch;
    private TextView instantBookingEdit;
    private TextView curbsideDeliveryTitle;
    private AppCompatImageView curbsideDeliveryIcon;
    private SwitchCompat curbsideDeliverySwitch;
    private TextView curbsideDeliveryEdit;
    private TextView acceptCashTitle;
    private AppCompatImageView acceptCashImage;
    private SwitchCompat acceptCashSwitch;
    private View rentalRatesEdit;
    private TextView rentalRatesValueFirst; //1 of 3 (temporary)
    private TextView rentalRatesValueSecond;
    private TextView rentalDiscount;
    private View fuelPolicyEdit;
    private TextView fuelPolicyValue;
    private View rentalTermsEdit;

    private RentalDetails dailyRental;
    private StrategyRentalDetailPresenter presenter;
    private ChildProgressListener progressListener;
    private DateStringDelegate stringDelegate;
    private Toast currentToast;


    public DailyRentalViewHolder(@NonNull View rootView, @NonNull AppCompatActivity activity) {
        super(rootView, activity);
        DailyTimingEventBus.getInstance().setListener(this);
        presenter = new StrategyRentalDetailPresenter(this, StrategyRentalDetailPresenter.Strategy.DAILY);
        availabilityDays = rootView.findViewById(R.id.availability_days);
        timingPickup = rootView.findViewById(R.id.tv_rentalAvailableTimingPickup);
        timingReturn = rootView.findViewById(R.id.tv_rentalAvailableTimingReturn);
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
        rentalRatesValueFirst = rootView.findViewById(R.id.tv_rentalValueFirst);
        rentalRatesValueSecond = rootView.findViewById(R.id.tv_rentalValueSecond);
        rentalDiscount = rootView.findViewById(R.id.tv_rentalDiscount);
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
        initResources(activity);
    }

    private void initResources(Context context) {
        setInstantViewsState(instantBookingSwitch.isChecked());
        setDeliveryViewsState(curbsideDeliverySwitch.isChecked());
        setCashViewState(acceptCashSwitch.isChecked());
        stringDelegate = new DateStringDelegate(context);
    }

    @Override
    public void bind(RentalDetails model) {
        dailyRental = model;
        presenter.onBind(model);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cl_rentalTermsContainer:
                getActivity().startActivity(new Intent(getActivity(),
                        RentalTermsActivity.class));
                break;
            case R.id.tv_rentalFuelEdit:
                Intent iFuel = new Intent(getActivity(),
                        RentalFuelPolicyActivity.class);
                iFuel.putExtra(OwnerCarRentalFragment.MODE, OwnerCarRentalFragment.DAILY);
                getActivity().startActivity(iFuel);
                break;
            case R.id.tv_rentalAvailabilityEdit:
                Intent intent = new Intent(getActivity(), AvailabilityCalendarActivity.class);
                Bundle args = new Bundle();
                args.putInt(AvailabilityContract.CAR_ID, dailyRental.getCarId());
                args.putSerializable(AvailabilityContract.CALENDAR_MODE, AvailabilityContract.Mode.DAILY);
                intent.putExtras(args);
                getActivity().startActivity(intent);
                break;
            case R.id.tv_rentalTimingEdit:
                DailyAvailabilityTimingFragment.newInstance(
                        stringDelegate.getSimpleTimeFormat(dailyRental.getDailyTimePickup()),
                        stringDelegate.getSimpleTimeFormat(dailyRental.getDailyTimeReturn()))
                        .show(getActivity().getSupportFragmentManager(),
                                DailyAvailabilityTimingFragment.class.getSimpleName());
                break;
            case R.id.tv_rentalInstantEdit:
                BookingPickerMenuFragment menu = BookingPickerMenuFragment.getInstance(instantBookingEdit.getText().toString(),
                        BookingPickerMenuFragment.Mode.BOOKING_DAYS);
                menu.show(getActivity().getSupportFragmentManager(), menu.getTag());
                menu.setOnDoneClickListener(new PickerMenuFragment.DialogOnClickListener() {
                    @Override
                    public void onDoneClicked(String value) {
                        instantBookingEdit.setText(value);
                    }
                });
                break;
            case R.id.tv_rentalCurbsideRatesEdit:
                break;
            case R.id.tv_rentalRentalRatesEdit:
                Intent iRates = new Intent(getActivity(),
                        RentalRatesActivity.class);
                iRates.putExtra(RentalRatesActivity.RATE_FIRST, String.valueOf(dailyRental.getDailyAmountRateFirst()));
                iRates.putExtra(RentalRatesActivity.RATE_SECOND, String.valueOf(dailyRental.getDailyAmountRateSecond()));
                iRates.putExtra(RentalRatesActivity.DISCOUNT_FIRST, String.valueOf(Math.round(dailyRental.getDailyAmountDiscountFirst())));
                iRates.putExtra(RentalRatesActivity.DISCOUNT_SECOND, String.valueOf(Math.round(dailyRental.getDailyAmountDiscountSecond())));
                iRates.putExtra(RentalRatesActivity.MIN_RENTAL, dailyRental.getDailyMinRentalDuration());
                iRates.putExtra(OwnerCarRentalFragment.MODE, OwnerCarRentalFragment.DAILY);
                getActivity().startActivity(iRates);
                break;
            case R.id.iv_rentalHelp:
                showInfoDialog();
                break;
        }
    }

    private void showInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_rental_info_settings, null);
        builder.setView(rootView);
        final Dialog dialog = builder.create();
        AppCompatButton buttonOk = rootView.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.sw_rentalInstant:
                setInstantViewsState(b);
                break;
            case R.id.sw_rentalDelivery:
                setDeliveryViewsState(b);
                break;
            case R.id.sw_rentalCash:
                setCashViewState(b);
                break;
        }
    }

    private void setInstantViewsState(boolean enabled) {
        instantBookingEdit.setClickable(enabled);
        if (enabled) {
            instantBookingIcon.setImageResource(R.drawable.ic_instant);
            instantBookingTitle.setTextColor(getActivity().getResources().getColor(R.color.text_enabled));
            instantBookingEdit.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        } else {
            instantBookingIcon.setImageResource(R.drawable.ic_instant_inactive);
            instantBookingTitle.setTextColor(getActivity().getResources().getColor(R.color.text_disabled));
            instantBookingEdit.setTextColor(getActivity().getResources().getColor(R.color.text_disabled));
        }
    }

    private void setDeliveryViewsState(boolean enabled) {
        curbsideDeliveryEdit.setClickable(enabled);
        if (enabled) {
            curbsideDeliveryIcon.setImageResource(R.drawable.ic_direction);
            curbsideDeliveryTitle.setTextColor(getActivity().getResources().getColor(R.color.text_enabled));
            curbsideDeliveryEdit.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        } else {
            curbsideDeliveryIcon.setImageResource(R.drawable.ic_direction_inactive);
            curbsideDeliveryTitle.setTextColor(getActivity().getResources().getColor(R.color.text_disabled));
            curbsideDeliveryEdit.setTextColor(getActivity().getResources().getColor(R.color.text_disabled));
        }
    }

    private void setCashViewState(boolean enabled) {
        if (enabled) {
            acceptCashImage.setImageResource(R.drawable.ic_cash);
            acceptCashTitle.setTextColor(getActivity().getResources().getColor(R.color.text_enabled));
        } else {
            acceptCashImage.setImageResource(R.drawable.ic_cash_inactive);
            acceptCashTitle.setTextColor(getActivity().getResources().getColor(R.color.text_disabled));
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
        stringDelegate.onSetValue(availabilityDays, rentalDetails.getDailyCount());
        stringDelegate.onSetPickupTime(timingPickup, rentalDetails.getDailyTimePickup());
        stringDelegate.onSetReturnTime(timingReturn, rentalDetails.getDailyTimeReturn());
        stringDelegate.onSetDailyRentalRateFirst(rentalRatesValueFirst, rentalDetails.getDailyAmountRateFirst());
        stringDelegate.onSetDailyRentalRateSecond(rentalRatesValueSecond, rentalDetails.getDailyAmountRateSecond());
        stringDelegate.onSetDailyRentalDiscount(rentalDiscount, rentalDetails.getDailyAmountDiscountFirst());
        stringDelegate.onSetFuelPolicy(fuelPolicyValue, rentalDetails.getDailyFuelPolicyName(), "");
    }

    @Override
    public void onInstantEnabled(boolean enabled) {
        instantBookingSwitch.setOnCheckedChangeListener(null);
        instantBookingSwitch.setChecked(enabled);
        instantBookingSwitch.setOnCheckedChangeListener(presenter);
    }

    @Override
    public void onCurbsideEnabled(boolean enabled) {
        curbsideDeliverySwitch.setOnCheckedChangeListener(null);
        curbsideDeliverySwitch.setChecked(enabled);
        curbsideDeliverySwitch.setOnCheckedChangeListener(presenter);
    }

    @Override
    public void onCashEnabled(boolean enabled) {
        acceptCashSwitch.setOnCheckedChangeListener(null);
        acceptCashSwitch.setChecked(enabled);
        acceptCashSwitch.setOnCheckedChangeListener(presenter);
    }

    @Override
    public void onSave(TimingSaveEvent event) {
        String pickupTime = stringDelegate.getGMTTimeString(event.getHourBegin());
        String returnTime = stringDelegate.getGMTTimeString(event.getHourEnd());
        stringDelegate.onSetPickupTime(timingPickup, pickupTime);
        stringDelegate.onSetReturnTime(timingReturn, returnTime);
        presenter.updateAvailabilityTiming(pickupTime, returnTime);
    }
}

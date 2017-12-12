package com.cardee.owner_car_details.presenter;

import android.widget.CompoundButton;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.entity.RentalDetails;
import com.cardee.domain.owner.usecase.SaveDailyRental;
import com.cardee.domain.owner.usecase.SaveHourlyTiming;
import com.cardee.owner_car_details.RentalDetailsContract;

public class StrategyRentalDetailPresenter
        implements RentalDetailsContract.Presenter, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = StrategyRentalDetailPresenter.class.getSimpleName();

    public enum Strategy {
        DAILY, HOURLY
    }

    private final Strategy strategy;
    private final UseCaseExecutor executor;
    private final SaveHourlyTiming saveHourlyDates;
    private final SaveDailyRental saveDailyRental;
    private RentalDetailsContract.ControlView view;
    private RentalDetails rentalDetails;

    public StrategyRentalDetailPresenter(RentalDetailsContract.ControlView view, Strategy strategy) {
        this.strategy = strategy;
        this.view = view;
        executor = UseCaseExecutor.getInstance();
        saveHourlyDates = new SaveHourlyTiming();
        saveDailyRental = new SaveDailyRental();
    }

    @Override
    public void init() {

    }

    public void onBind(RentalDetails rentalDetails) {
        this.rentalDetails = rentalDetails;
        if (view != null) {
            view.setData(rentalDetails);
        }
    }

    public void updateAvailabilityTiming(String beginTime, String endTime) {
        if (rentalDetails == null) {
            return;
        }
        switch (strategy) {
            case HOURLY:
                updateHourlyTiming(beginTime, endTime);
                break;
            case DAILY:
                updateDailyTiming(beginTime, endTime);
                break;
        }
    }

    private void updateDailyTiming(String pickupTiming, String returnTiming) {
        final RentalDetails requestBody = RentalDetails.from(rentalDetails);
        requestBody.setDailyTimePickup(pickupTiming);
        requestBody.setDailyTimeReturn(returnTiming);
        executor.execute(saveDailyRental, new SaveDailyRental.RequestValues(requestBody),
                new UseCase.Callback<SaveDailyRental.ResponseValues>() {
                    @Override
                    public void onSuccess(SaveDailyRental.ResponseValues response) {
                        rentalDetails = requestBody;
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.setData(rentalDetails);
                        }
                    }
                });
    }

    private void updateHourlyTiming(final String beginTiming, final String endTiming) {
        executor.execute(saveHourlyDates,
                new SaveHourlyTiming.RequestValues(rentalDetails.getCarId(), beginTiming, endTiming, rentalDetails.getHourlyDates()),
                new UseCase.Callback<SaveHourlyTiming.ResponseValues>() {
                    @Override
                    public void onSuccess(SaveHourlyTiming.ResponseValues response) {
                        rentalDetails.setHourlyBeginTime(beginTiming);
                        rentalDetails.setHourlyEndTime(endTiming);
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.setData(rentalDetails);
                        }
                    }
                });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.sw_rentalInstant:
            case R.id.sw_rentalDelivery:
            case R.id.sw_rentalCash:
                if (view != null) {
                    view.showMessage("Checked: " + b);
                }
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}

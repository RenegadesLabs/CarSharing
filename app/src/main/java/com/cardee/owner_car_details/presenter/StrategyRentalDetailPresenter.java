package com.cardee.owner_car_details.presenter;

import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.data_source.OwnerCarsRepository;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.entity.RentalDetails;
import com.cardee.domain.owner.usecase.SaveDailyRental;
import com.cardee.domain.owner.usecase.SaveHourlyTiming;
import com.cardee.domain.owner.usecase.UpdateDailyAcceptCashState;
import com.cardee.domain.owner.usecase.UpdateDailyCurbsideDeliveryState;
import com.cardee.domain.owner.usecase.UpdateDailyInstantBookingState;
import com.cardee.domain.owner.usecase.UpdateDailyinstantBookingCount;
import com.cardee.domain.owner.usecase.UpdateHourlyAcceptCashState;
import com.cardee.domain.owner.usecase.UpdateHourlyCurbsideDeliveryState;
import com.cardee.domain.owner.usecase.UpdateHourlyInstantBookingCount;
import com.cardee.domain.owner.usecase.UpdateHourlyInstantBookingState;
import com.cardee.owner_car_details.RentalDetailsContract;

public class StrategyRentalDetailPresenter implements RentalDetailsContract.Presenter {

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

    public void updateInstantBooking(boolean isInstantBooking) {
        if (rentalDetails == null) {
            return;
        }
        switch (strategy) {
            case DAILY:
                updateDailyInstantBooking(isInstantBooking);
                break;
            case HOURLY:
                updateHourlyInstantBooking(isInstantBooking);
                break;
        }
    }

    public void updateInstantBookingCount(int count) {
        if (rentalDetails == null) {
            return;
        }
        switch (strategy) {
            case DAILY:
                updateDailyInstantBookingCount(count);
                break;
            case HOURLY:
                updateHourlyInstantBookingCount(count);
                break;
        }
    }

    public void updateCurbsideDelivery(boolean isCurbsideDelivery) {
        if (rentalDetails == null) {
            return;
        }
        switch (strategy) {
            case DAILY:
                updateDailyCurbsideDelivery(isCurbsideDelivery);
                break;
            case HOURLY:
                updateHourlyCurbsideDelivery(isCurbsideDelivery);
                break;
        }
    }

    public void updateAcceptCash(boolean acceptCash) {
        if (rentalDetails == null) {
            return;
        }
        switch (strategy) {
            case DAILY:
                updateDailyAcceptCash(acceptCash);
                break;
            case HOURLY:
                updateHourlyAcceptCash(acceptCash);
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

    private void updateDailyInstantBooking(boolean isInstantBooking) {
        executor.execute(new UpdateDailyInstantBookingState(),
                new UpdateDailyInstantBookingState.RequestValues(rentalDetails.getCarId(), isInstantBooking),
                new UseCase.Callback<UpdateDailyInstantBookingState.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateDailyInstantBookingState.ResponseValues response) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails.getCarId());
                        OwnerCarsRepository.getInstance().refreshCars();
                        rentalDetails.setDailyInstantBooking(isInstantBooking);
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.setData(rentalDetails);
                        }
                    }
                });
    }

    private void updateHourlyInstantBooking(boolean isInstantBooking) {
        executor.execute(new UpdateHourlyInstantBookingState(),
                new UpdateHourlyInstantBookingState.RequestValues(rentalDetails.getCarId(), isInstantBooking),
                new UseCase.Callback<UpdateHourlyInstantBookingState.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateHourlyInstantBookingState.ResponseValues response) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails.getCarId());
                        OwnerCarsRepository.getInstance().refreshCars();
                        rentalDetails.setHourlyInstantBooking(isInstantBooking);
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.setData(rentalDetails);
                        }
                    }
                });
    }

    private void updateDailyInstantBookingCount(int count) {
        executor.execute(new UpdateDailyinstantBookingCount(),
                new UpdateDailyinstantBookingCount.RequestValues(rentalDetails.getCarId(), count),
                new UseCase.Callback<UpdateDailyinstantBookingCount.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateDailyinstantBookingCount.ResponseValues response) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails.getCarId());
                        OwnerCarsRepository.getInstance().refreshCars();
                        rentalDetails.setDailyInstantBookingCount(count);
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.setData(rentalDetails);
                        }
                    }
                });
    }

    private void updateHourlyInstantBookingCount(int count) {
        executor.execute(new UpdateHourlyInstantBookingCount(),
                new UpdateHourlyInstantBookingCount.RequestValues(rentalDetails.getCarId(), count),
                new UseCase.Callback<UpdateHourlyInstantBookingCount.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateHourlyInstantBookingCount.ResponseValues response) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails.getCarId());
                        OwnerCarsRepository.getInstance().refreshCars();
                        rentalDetails.setHourlyInstantBookingCount(count);
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.setData(rentalDetails);
                        }
                    }
                });
    }

    private void updateDailyCurbsideDelivery(boolean isCurbsideDelivery) {
        executor.execute(new UpdateDailyCurbsideDeliveryState(),
                new UpdateDailyCurbsideDeliveryState.RequestValues(rentalDetails.getCarId(), isCurbsideDelivery),
                new UseCase.Callback<UpdateDailyCurbsideDeliveryState.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateDailyCurbsideDeliveryState.ResponseValues response) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails.getCarId());
                        OwnerCarsRepository.getInstance().refreshCars();
                        rentalDetails.setDailyCurbsideDelivery(isCurbsideDelivery);
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.setData(rentalDetails);
                        }
                    }
                });
    }

    private void updateHourlyCurbsideDelivery(boolean isCurbsideDelivery) {
        executor.execute(new UpdateHourlyCurbsideDeliveryState(),
                new UpdateHourlyCurbsideDeliveryState.RequestValues(rentalDetails.getCarId(), isCurbsideDelivery),
                new UseCase.Callback<UpdateHourlyCurbsideDeliveryState.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateHourlyCurbsideDeliveryState.ResponseValues response) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails.getCarId());
                        OwnerCarsRepository.getInstance().refreshCars();
                        rentalDetails.setHourlyCurbsideDelivery(isCurbsideDelivery);
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.setData(rentalDetails);
                        }
                    }
                });
    }

    private void updateDailyAcceptCash(boolean acceptCash) {
        executor.execute(new UpdateDailyAcceptCashState(),
                new UpdateDailyAcceptCashState.RequestValues(rentalDetails.getCarId(), acceptCash),
                new UseCase.Callback<UpdateDailyAcceptCashState.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateDailyAcceptCashState.ResponseValues response) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails.getCarId());
                        OwnerCarsRepository.getInstance().refreshCars();
                        rentalDetails.setDailyAcceptCash(acceptCash);
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.setData(rentalDetails);
                        }
                    }
                });
    }

    private void updateHourlyAcceptCash(boolean acceptCash) {
        executor.execute(new UpdateHourlyAcceptCashState(),
                new UpdateHourlyAcceptCashState.RequestValues(rentalDetails.getCarId(), acceptCash),
                new UseCase.Callback<UpdateHourlyAcceptCashState.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateHourlyAcceptCashState.ResponseValues response) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails.getCarId());
                        OwnerCarsRepository.getInstance().refreshCars();
                        rentalDetails.setHourlyAcceptCash(acceptCash);
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
    public void onDestroy() {
        view = null;
    }
}

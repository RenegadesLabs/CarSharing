package com.cardee.owner_home.presenter;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.owner.usecase.GetCars;
import com.cardee.domain.owner.usecase.SaveAvailability;
import com.cardee.owner_home.OwnerCarListContract;
import com.crashlytics.android.Crashlytics;

import java.util.List;

import io.reactivex.functions.Consumer;

public class OwnerCarsPresenter implements Consumer<OwnerCarListContract.CarEvent> {

    private OwnerCarListContract.View mView;
    private final GetCars mGetCars;
    private final SaveAvailability saveAvailability;
    private final UseCaseExecutor mExecutor;

    private boolean firstStart = true;

    public OwnerCarsPresenter(OwnerCarListContract.View view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
        mGetCars = new GetCars();
        saveAvailability = new SaveAvailability();
    }

    public void loadItems() {
        if (firstStart) {
            mView.showProgress(true);
        }
        GetCars.RequestValues requestValues = new GetCars.RequestValues(firstStart);
        mExecutor.execute(mGetCars, requestValues, new UseCase.Callback<GetCars.ResponseValues>() {
            @Override
            public void onSuccess(GetCars.ResponseValues response) {
                List<Car> cars = response.getCars();
                if (mView != null) {
                    mView.showProgress(false);
                    mView.setItems(cars);
                    firstStart = false;
                } else {
                    Crashlytics.logException(new Throwable("Success: View is null"));
                }
            }

            @Override
            public void onError(Error error) {
                if (mView != null) {
                    mView.showProgress(false);
                    handleError(error);
                }
                Crashlytics.logException(new Throwable("Error: " + error.getMessage()));
            }
        });
    }

    private void handleError(Error error) {

        if (error.isAuthError()) {
            mView.onUnauthorized();
        } else if (error.isConnectionError()) {
            mView.onConnectionLost();
        } else {
            mView.showMessage(error.getMessage());
        }
    }

    public void refresh() {
        firstStart = true;
    }

    public void destroy() {
        if (mGetCars != null) {
            mView.showProgress(false);
        }
        mView = null;
    }

    @Override
    public void accept(OwnerCarListContract.CarEvent carEvent) throws Exception {
        switch (carEvent.getAction()) {
            case DAILY_CLICKED:
                mView.openDailyPicker(carEvent.getCar());
                break;
            case HOURLY_CLICKED:
                mView.openHourlyPicker(carEvent.getCar());
                break;
            case LOCATION_CLICKED:
                mView.openLocationPicker(carEvent.getCar());
                break;
            case OPEN:
                mView.openItem(carEvent.getCar());
                break;
            case HOURLY_SWITCHED:
                changeAvailability(carEvent.getCar(), carEvent.getAction());
                break;
            case DAILY_SWITCHED:
                changeAvailability(carEvent.getCar(), carEvent.getAction());
                break;
        }
    }

    private void changeAvailability(Car car, OwnerCarListContract.Action action) {
        boolean availableHourly = (OwnerCarListContract.Action.HOURLY_SWITCHED == action) != car.isAvailableHourly();
        boolean availableDaily = (OwnerCarListContract.Action.DAILY_SWITCHED == action) != car.isAvailableDaily();
        SaveAvailability.RequestValues request =
                new SaveAvailability.RequestValues(car.getCarId(), availableDaily, availableHourly);
        mExecutor.execute(saveAvailability, request, new UseCase.Callback<SaveAvailability.ResponseValues>() {
            @Override
            public void onSuccess(SaveAvailability.ResponseValues response) {
                Car updated = car.newBuilder()
                        .setAvailableHourly(availableHourly)
                        .setAvailableDaily(availableDaily)
                        .build();
                if (mView != null) {
                    onChangeAvailability(updated);
                }
            }

            @Override
            public void onError(Error error) {
                if (mView != null) {
                    mView.showMessage(error.getMessage());
                    onChangeAvailability(car);
                }
            }
        });
    }

    private void onChangeAvailability(Car car) {
        mView.updateItem(car);
    }
}

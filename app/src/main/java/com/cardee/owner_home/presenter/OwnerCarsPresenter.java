package com.cardee.owner_home.presenter;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.owner.usecase.GetCars;
import com.cardee.domain.owner.usecase.SwitchDaily;
import com.cardee.domain.owner.usecase.SwitchHourly;
import com.cardee.owner_home.OwnerCarListContract;

import java.util.List;

import io.reactivex.functions.Consumer;

public class OwnerCarsPresenter implements Consumer<OwnerCarListContract.CarEvent> {

    private OwnerCarListContract.View mView;

    private final GetCars mGetCars;
    private final SwitchDaily mSwitchDaily;
    private final SwitchHourly mSwitchHourly;

    private final UseCaseExecutor mExecutor;

    private boolean firstStart = true;

    public OwnerCarsPresenter(OwnerCarListContract.View view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();

        mGetCars = new GetCars();
        mSwitchDaily = new SwitchDaily();
        mSwitchHourly = new SwitchHourly();
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
                }
            }

            @Override
            public void onError(Error error) {
                if (mView != null) {
                    mView.showProgress(false);
                    handleError(error);
                }
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
            case DAILY_SWITCHED:
                mView.showMessage("Coming soon"); //PLUG
                break;
        }
    }
}

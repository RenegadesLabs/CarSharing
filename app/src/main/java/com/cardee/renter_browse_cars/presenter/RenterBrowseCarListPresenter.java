package com.cardee.renter_browse_cars.presenter;


import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.renter.entity.OfferCar;
import com.cardee.domain.renter.usecase.GetCars;
import com.crashlytics.android.Crashlytics;

import java.util.List;

import io.reactivex.functions.Consumer;

public class RenterBrowseCarListPresenter implements Consumer<RenterBrowseCarListContract.CarEvent> {

    private final UseCaseExecutor mExecutor;
    private RenterBrowseCarListContract.View mView;

    private boolean firstStart = true;


    public RenterBrowseCarListPresenter(RenterBrowseCarListContract.View view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void accept(RenterBrowseCarListContract.CarEvent carEvent) throws Exception {

        switch (carEvent.getAction()) {
            case UPDATED:
                break;

            case OPEN:
                break;

            case FAVORITE:
                break;
        }

    }

    public void loadItems() {
        if (firstStart && mView != null) {
            mView.showProgress(true);
        }

        mExecutor.execute(new GetCars(), new GetCars.RequestValues(), new UseCase.Callback<GetCars.ResponseValues>() {
            @Override
            public void onSuccess(GetCars.ResponseValues response) {
                List<OfferCar> cars = response.getOfferCars();
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
                handleError(error);
            }
        });

    }

    public void refresh() {
        firstStart = true;
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
}

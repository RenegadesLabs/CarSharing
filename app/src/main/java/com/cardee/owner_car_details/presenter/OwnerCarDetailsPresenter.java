package com.cardee.owner_car_details.presenter;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.GetCar;
import com.cardee.owner_car_add.NewCarFormsContract;
import com.cardee.owner_car_details.OwnerCarDetailsContract;

import io.reactivex.functions.Consumer;

public class OwnerCarDetailsPresenter
        implements OwnerCarDetailsContract.Presenter, Consumer<OwnerCarDetailsContract.CarDetailEvent> {

    private static final int PROGRESS_CANCEL_TIMEOUT = 300;

    private final UseCaseExecutor mExecutor;
    private final GetCar mGetCar;

    private boolean mNeedProgress;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private OwnerCarDetailsContract.View mView;
    private int mCarId;

    public OwnerCarDetailsPresenter(OwnerCarDetailsContract.View view, int carId) {
        mView = view;
        mCarId = carId;

        mExecutor = UseCaseExecutor.getInstance();
        mGetCar = new GetCar();
    }

    public void get() {
        showCancelableProgress();
        GetCar.RequestValues values = new GetCar.RequestValues(mCarId);
        mExecutor.execute(mGetCar, values, new UseCase.Callback<GetCar.ResponseValues>() {
            @Override
            public void onSuccess(GetCar.ResponseValues response) {
                if (mView != null) {
                    cancelProgress();
                    mView.setCar(response.getCar());
                }
            }

            @Override
            public void onError(Error error) {
                if (mView != null) {
                    cancelProgress();
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

    public void destroy() {
        mView = null;
    }

    @Override
    public void accept(OwnerCarDetailsContract.CarDetailEvent event) throws Exception {
        Bundle args = new Bundle();
        args.putInt(NewCarFormsContract.CAR_ID, mCarId);
        switch (event.getAction()) {
            case EDIT_IMAGES:
                mView.moveToImages(null);
                break;
            case EDIT_SPECS:
                mView.moveToSpecs(null);
                break;
            case EDIT_LOCATION:
                args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.LOCATION);
                mView.moveToLocation(args);
                break;
            case EDIT_DESCRIPTION:
                mView.moveToDescription(null);
                break;
        }
    }

    private void showCancelableProgress() {
        mNeedProgress = true;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mNeedProgress && mView != null) {
                    mView.showProgress(mNeedProgress);
                    mNeedProgress = false;
                }
            }
        }, PROGRESS_CANCEL_TIMEOUT);
    }

    private void cancelProgress() {
        mNeedProgress = false;
        mView.showProgress(false);
    }
}

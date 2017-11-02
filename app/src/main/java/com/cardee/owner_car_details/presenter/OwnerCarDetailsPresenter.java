package com.cardee.owner_car_details.presenter;


import com.cardee.owner_car_details.OwnerCarDetailsContract;

public class OwnerCarDetailsPresenter implements OwnerCarDetailsContract.Presenter {

    private OwnerCarDetailsContract.View mView;
    private int mCarId;

    public OwnerCarDetailsPresenter(OwnerCarDetailsContract.View view, int carId) {
        mView = view;
        mCarId = carId;
    }

    public void get() {

    }

    public void destroy() {
        mView = null;
    }
}

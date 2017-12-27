package com.cardee.renter_bookings.rate_rental_exp.presenter;


import com.cardee.mvp.BasePresenter;
import com.cardee.renter_bookings.rate_rental_exp.view.RateRentalExpView;

public class RateRentalExpPresenter implements BasePresenter {

    private RateRentalExpView mView;

    public RateRentalExpPresenter(RateRentalExpView view) {
        mView = view;
    }

    @Override
    public void init() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}

package com.cardee.owner_bookings.presenter;

import com.cardee.owner_bookings.OwnerBookingContract;


public class OwnerBookingPresenter implements OwnerBookingContract.Presenter {

    private OwnerBookingContract.View view;

    private final int bookingId;

    public OwnerBookingPresenter(int bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public void setView(OwnerBookingContract.View view) {
        this.view = view;
    }

    @Override
    public void init() {

    }

    @Override
    public void onDestroy() {
        view = null;
    }
}

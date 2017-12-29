package com.cardee.owner_bookings.presenter;

import com.cardee.owner_bookings.OwnerBookingContract;
import com.cardee.owner_bookings.strategy.PresentationStrategy;


public class OwnerBookingPresenter implements OwnerBookingContract.Presenter {

    private OwnerBookingContract.View view;
    private PresentationStrategy strategy;
    private final int bookingId;

    public OwnerBookingPresenter(int bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public void setView(OwnerBookingContract.View view) {
        this.view = view;
    }

    @Override
    public void setStrategy(PresentationStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void init() {

    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onDecline() {

    }

    @Override
    public void onAccept() {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onHandOver() {

    }

    @Override
    public void onCancelHandOver() {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onShowProfile() {

    }

    @Override
    public void onCall() {

    }

    @Override
    public void onChat() {

    }
}

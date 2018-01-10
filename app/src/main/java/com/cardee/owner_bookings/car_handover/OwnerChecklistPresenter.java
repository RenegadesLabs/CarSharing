package com.cardee.owner_bookings.car_handover;

import com.cardee.owner_bookings.car_handover.strategy.PresentationStrategy;


public class OwnerChecklistPresenter implements HandoverContract.Presenter {

    private HandoverContract.View mView;

    public OwnerChecklistPresenter() {

    }

    @Override
    public void setView(HandoverContract.View view) {
        mView = view;
    }

    @Override
    public void setStrategy(PresentationStrategy strategy) {

    }

    @Override
    public void init() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onHandover() {

    }

    @Override
    public void onAccurateCancel() {

    }

    @Override
    public void onAccurateConfirm() {

    }
}

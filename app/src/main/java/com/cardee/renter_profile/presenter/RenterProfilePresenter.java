package com.cardee.renter_profile.presenter;


import com.cardee.domain.UseCaseExecutor;
import com.cardee.renter_profile.view.RenterProfileView;

public class RenterProfilePresenter {

    private final String TAG = this.getClass().getSimpleName();
    private RenterProfileView mView;
    private UseCaseExecutor mExecutor;

    public RenterProfilePresenter(RenterProfileView view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }
}

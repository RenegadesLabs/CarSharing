package com.cardee.owner_car_details.presenter;

import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.GetRentalDetails;
import com.cardee.mvp.BasePresenter;


public class RentalDetailsPresenter implements BasePresenter {

    private final int id;
    private final GetRentalDetails getRentalDetails;
    private final UseCaseExecutor executor;

    public RentalDetailsPresenter(int id) {
        this.id = id;
        getRentalDetails = new GetRentalDetails();
        executor = UseCaseExecutor.getInstance();
    }

    @Override
    public void init() {

    }

    @Override
    public void onDestroy() {

    }
}

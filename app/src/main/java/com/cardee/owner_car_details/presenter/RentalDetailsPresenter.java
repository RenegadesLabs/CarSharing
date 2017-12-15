package com.cardee.owner_car_details.presenter;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.GetRentalDetails;
import com.cardee.mvp.BasePresenter;
import com.cardee.owner_car_details.view.listener.RentalDetailsListener;


public class RentalDetailsPresenter implements BasePresenter {

    private final int id;
    private final GetRentalDetails getRentalDetails;
    private final UseCaseExecutor executor;
    private final RentalDetailsListener mListener;

    public RentalDetailsPresenter(int id, RentalDetailsListener listener) {
        this.id = id;
        getRentalDetails = new GetRentalDetails();
        executor = UseCaseExecutor.getInstance();
        mListener = listener;
    }

    @Override
    public void init() {

    }

    public void getRentalDetails() {
        executor.execute(getRentalDetails, new GetRentalDetails.RequestValues(id),
                new UseCase.Callback<GetRentalDetails.ResponseValues>() {
            @Override
            public void onSuccess(GetRentalDetails.ResponseValues response) {
                if (mListener == null)
                    return;

                mListener.onRentalDetailsFetched(response.getRentalDetails());
            }

            @Override
            public void onError(Error error) {

            }
        });
    }


    @Override
    public void onDestroy() {

    }
}

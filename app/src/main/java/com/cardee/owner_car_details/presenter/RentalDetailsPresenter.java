package com.cardee.owner_car_details.presenter;

import android.widget.CompoundButton;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.entity.RentalDetails;
import com.cardee.domain.owner.usecase.GetRentalDetails;
import com.cardee.domain.owner.usecase.SaveAvailability;
import com.cardee.owner_car_details.RentalDetailsContract;


public class RentalDetailsPresenter
        implements RentalDetailsContract.Presenter, CompoundButton.OnCheckedChangeListener {

    private RentalDetailsContract.View view;

    private final int id;
    private final GetRentalDetails getRentalDetails;
    private final SaveAvailability setDailyAvailability;
    private final UseCaseExecutor executor;
    private RentalDetails rentalDetails;

    public RentalDetailsPresenter(RentalDetailsContract.View view, int id) {
        this.view = view;
        this.id = id;
        getRentalDetails = new GetRentalDetails();
        setDailyAvailability = new SaveAvailability();
        executor = UseCaseExecutor.getInstance();
    }

    @Override
    public void init() {
        executor.execute(getRentalDetails, new GetRentalDetails.RequestValues(id),
                new UseCase.Callback<GetRentalDetails.ResponseValues>() {
                    @Override
                    public void onSuccess(GetRentalDetails.ResponseValues response) {
                        if (view != null) {
                            view.setData(response.getRentalDetails());
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.showMessage(error.getMessage());
                        }
                    }
                });
    }

    public void setRentalDetails(RentalDetails rentalDetails) {
        this.rentalDetails = rentalDetails;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.sw_rentalMainDaily:
                toggleDailyAvailability(b);
                break;
            case R.id.sw_rentalMainHourly:
                toggleHourlyAvailability(b);
                break;
        }
    }

    public void toggleDailyAvailability(final boolean available) {
        if (rentalDetails == null) {
            return;
        }
        executor.execute(setDailyAvailability, new SaveAvailability.RequestValues(id,
                        available, rentalDetails.isAvailableHourly()),
                new UseCase.Callback<SaveAvailability.ResponseValues>() {
                    @Override
                    public void onSuccess(SaveAvailability.ResponseValues response) {
                        rentalDetails.setAvailableDaily(available);
                        if (view != null){
                            view.onDailyAvailChanged(rentalDetails);
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.onDailyChange(rentalDetails.isAvailableDaily());
                            view.showMessage(error.getMessage());
                        }
                    }
                });
    }

    public void toggleHourlyAvailability(final boolean available) {
        if (rentalDetails == null) {
            return;
        }
        executor.execute(setDailyAvailability, new SaveAvailability.RequestValues(id,
                        rentalDetails.isAvailableDaily(), available),
                new UseCase.Callback<SaveAvailability.ResponseValues>() {
                    @Override
                    public void onSuccess(SaveAvailability.ResponseValues response) {
                        rentalDetails.setAvailableHourly(available);
                        if (view != null){
                            view.onHourlyAvailChanged(rentalDetails);
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.onDailyChange(rentalDetails.isAvailableDaily());
                            view.showMessage(error.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}

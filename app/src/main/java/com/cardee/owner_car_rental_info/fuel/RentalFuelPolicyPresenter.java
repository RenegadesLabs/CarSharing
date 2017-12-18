package com.cardee.owner_car_rental_info.fuel;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.data_source.remote.api.profile.response.entity.OwnerCar;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.UpdateFuelPolicyDaily;
import com.cardee.domain.owner.usecase.UpdateFuelPolicyHourly;
import com.cardee.owner_car_details.view.OwnerCarRentalFragment;
import com.cardee.owner_car_rental_info.RentalContract;

public class RentalFuelPolicyPresenter implements RentalContract.Presenter {

    private RentalContract.View mView;
    private final UseCaseExecutor mExecutor;


    public RentalFuelPolicyPresenter(RentalContract.View view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void save(Object... objects) {

        if (mView == null)
            return;

        mView.showProgress(true);

        int mode = (int) objects[0];
        int fuelPolicyId = (int) objects[1];

        switch (mode) {
            case OwnerCarRentalFragment.HOURLY:

                float amountPayMileage = (float) objects[2];
                mExecutor.execute(new UpdateFuelPolicyHourly(),
                        new UpdateFuelPolicyHourly.RequestValues(OwnerCarRepository.currentCarId(),
                                fuelPolicyId, amountPayMileage),
                        new UseCase.Callback<UpdateFuelPolicyHourly.ResponseValues>() {
                            @Override
                            public void onSuccess(UpdateFuelPolicyHourly.ResponseValues response) {
                                mView.showProgress(false);
                                if (response.isSuccess()) {
                                    mView.onSuccess();
                                    mView.showMessage(R.string.saved_successfully);
                                }
                            }

                            @Override
                            public void onError(Error error) {
                                mView.showProgress(false);
                                mView.showMessage(R.string.error_occurred);
                            }
                        });
                break;
            case OwnerCarRentalFragment.DAILY:
                mExecutor.execute(new UpdateFuelPolicyDaily(),
                        new UpdateFuelPolicyDaily.RequestValues(OwnerCarRepository.currentCarId(),
                                fuelPolicyId),
                        new UseCase.Callback<UpdateFuelPolicyDaily.ResponseValues>() {
                            @Override
                            public void onSuccess(UpdateFuelPolicyDaily.ResponseValues response) {
                                mView.showProgress(false);
                                if (response.isSuccess()) {
                                    mView.onSuccess();
                                    mView.showMessage(R.string.saved_successfully);
                                }
                            }

                            @Override
                            public void onError(Error error) {
                                mView.showProgress(false);
                                mView.showMessage(R.string.error_occurred);
                            }
                        });
                break;
        }

    }
}

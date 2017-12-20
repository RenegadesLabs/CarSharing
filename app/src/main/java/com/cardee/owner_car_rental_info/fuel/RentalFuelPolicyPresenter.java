package com.cardee.owner_car_rental_info.fuel;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.data_source.OwnerCarsRepository;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.UpdateDailyFuelPolicy;
import com.cardee.domain.owner.usecase.UpdateHourlyFuelPolicy;
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
                mExecutor.execute(new UpdateHourlyFuelPolicy(),
                        new UpdateHourlyFuelPolicy.RequestValues(OwnerCarRepository.currentCarId(),
                                fuelPolicyId, amountPayMileage),
                        new UseCase.Callback<UpdateHourlyFuelPolicy.ResponseValues>() {
                            @Override
                            public void onSuccess(UpdateHourlyFuelPolicy.ResponseValues response) {
                                mView.showProgress(false);
                                if (response.isSuccess()) {
                                    OwnerCarRepository.getInstance().refresh(OwnerCarRepository.currentCarId());
                                    OwnerCarsRepository.getInstance().refreshCars();
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
                mExecutor.execute(new UpdateDailyFuelPolicy(),
                        new UpdateDailyFuelPolicy.RequestValues(OwnerCarRepository.currentCarId(),
                                fuelPolicyId),
                        new UseCase.Callback<UpdateDailyFuelPolicy.ResponseValues>() {
                            @Override
                            public void onSuccess(UpdateDailyFuelPolicy.ResponseValues response) {
                                mView.showProgress(false);
                                if (response.isSuccess()) {
                                    OwnerCarRepository.getInstance().refresh(OwnerCarRepository.currentCarId());
                                    OwnerCarsRepository.getInstance().refreshCars();
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

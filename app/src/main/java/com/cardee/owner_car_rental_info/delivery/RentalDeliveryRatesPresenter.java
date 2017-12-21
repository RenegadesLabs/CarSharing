package com.cardee.owner_car_rental_info.delivery;


import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.data_source.OwnerCarsRepository;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.UpdateDeliveryRates;
import com.cardee.owner_car_rental_info.RentalContract;

public class RentalDeliveryRatesPresenter implements RentalContract.Presenter {

    private RentalContract.View mView;
    private UseCaseExecutor mExecutor;


    public RentalDeliveryRatesPresenter(RentalContract.View view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void save(Object... objects) {

        float baseRate = (float) objects[0];
        float distanceRate = (float) objects[1];
        boolean freeProvided = (boolean) objects[2];
        float rentalDuration = (float) objects[3];

        if (mView == null)
            return;

        mView.showProgress(true);

        mExecutor.execute(new UpdateDeliveryRates(),
                new UpdateDeliveryRates.RequestValues(OwnerCarRepository.currentCarId(),
                        baseRate, distanceRate, freeProvided, rentalDuration),
                new UseCase.Callback<UpdateDeliveryRates.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateDeliveryRates.ResponseValues response) {
                        mView.showProgress(false);
                        if (response.isSuccess()) {
                            OwnerCarRepository.getInstance().refresh(OwnerCarRepository.currentCarId());
                            OwnerCarsRepository.getInstance().refreshCars();
                            mView.showMessage(R.string.saved_successfully);
                            mView.onSuccess();
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        mView.showProgress(false);
                        mView.showMessage(R.string.error_occurred);
                    }
                });
    }
}

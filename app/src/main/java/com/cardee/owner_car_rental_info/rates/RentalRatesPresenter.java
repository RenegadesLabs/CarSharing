package com.cardee.owner_car_rental_info.rates;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.data_source.OwnerCarsRepository;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.UpdateDailyRentalRates;
import com.cardee.domain.owner.usecase.UpdateHourlyRentalRates;
import com.cardee.owner_car_details.view.OwnerCarRentalFragment;
import com.cardee.owner_car_rental_info.RentalContract;

public class RentalRatesPresenter implements RentalContract.Presenter {

    private final RentalContract.View mView;
    private final UseCaseExecutor mExecutor;

    public RentalRatesPresenter(RentalContract.View view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void save(Object... objects) {

        double firstRate = Double.parseDouble((String) objects[0]);
        double secondRate = Double.parseDouble((String) objects[1]);
        int firstDiscount = Integer.parseInt((String) objects[2]);
        int secondDiscount = Integer.parseInt((String) objects[3]);
        int minRental = Integer.parseInt((String) objects[4]);
        int mode = (int) objects[5];

        if (mView == null)
            return;

        mView.showProgress(true);

        if (mode == OwnerCarRentalFragment.DAILY) {
            mExecutor.execute(new UpdateDailyRentalRates(),
                    new UpdateDailyRentalRates.RequestValues(OwnerCarRepository.currentCarId(),
                            firstRate, secondRate, firstDiscount, secondDiscount, minRental),
                    new UseCase.Callback<UpdateDailyRentalRates.ResponseValues>() {
                        @Override
                        public void onSuccess(UpdateDailyRentalRates.ResponseValues response) {
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
            return;
        }

        mExecutor.execute(new UpdateHourlyRentalRates(),
                new UpdateHourlyRentalRates.RequestValues(OwnerCarRepository.currentCarId(),
                        firstRate, secondRate, firstDiscount, secondDiscount, minRental),
                new UseCase.Callback<UpdateHourlyRentalRates.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateHourlyRentalRates.ResponseValues response) {
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

package com.cardee.owner_car_rental_info.terms.presenter;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.data_source.OwnerCarsRepository;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.UpdateAdditionalTerms;
import com.cardee.owner_car_rental_info.RentalContract;

public class RentalTermsAdditionalPresenter implements RentalContract.Presenter {

    private final RentalContract.View mView;
    private UseCaseExecutor mExecutor;

    public RentalTermsAdditionalPresenter(RentalContract.View view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void save(Object... objects) {
        String value = (String) objects[0];
        if (mView == null)
            return;

        mView.showProgress(true);
        mExecutor.execute(new UpdateAdditionalTerms(),
                new UpdateAdditionalTerms.RequestValues(OwnerCarRepository.currentCarId(), value),
                new UseCase.Callback<UpdateAdditionalTerms.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateAdditionalTerms.ResponseValues response) {
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

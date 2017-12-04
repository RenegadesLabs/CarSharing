package com.cardee.owner_car_rental_info.terms.presenter;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.UpdateRentalInsuranceExcess;
import com.cardee.owner_car_rental_info.terms.RentalTermsContract;

public class RentalTermsInsurancePresenter implements RentalTermsContract.Presenter {

    private final RentalTermsContract.View mView;
    private UseCaseExecutor mExecutor;

    public RentalTermsInsurancePresenter(RentalTermsContract.View view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void save(Object... objects) {
        String value = (String) objects[0];
        if (mView == null)
            return;

        mView.showProgress(true);
        mExecutor.execute(new UpdateRentalInsuranceExcess(),
                new UpdateRentalInsuranceExcess.RequestValues(OwnerCarRepository.currentCarId(), value),
                new UseCase.Callback<UpdateRentalInsuranceExcess.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateRentalInsuranceExcess.ResponseValues response) {
                        mView.showProgress(false);
                        if (response.isSuccess()) {
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

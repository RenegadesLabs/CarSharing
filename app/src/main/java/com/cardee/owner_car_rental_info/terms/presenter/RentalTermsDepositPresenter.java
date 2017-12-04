package com.cardee.owner_car_rental_info.terms.presenter;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.UpdateRentalSecurityDeposit;
import com.cardee.owner_car_rental_info.terms.RentalTermsContract;

public class RentalTermsDepositPresenter implements RentalTermsContract.Presenter {

    private final RentalTermsContract.View mView;
    private UseCaseExecutor mExecutor;

    public RentalTermsDepositPresenter(RentalTermsContract.View view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void save(Object... objects) {
        boolean require = (boolean) objects[0];
        String amount = (String) objects[1];

        if (mView == null)
            return;

        mView.showProgress(true);
        mExecutor.execute(new UpdateRentalSecurityDeposit(),
                new UpdateRentalSecurityDeposit.RequestValues(OwnerCarRepository.currentCarId(), require, amount),
                new UseCase.Callback<UpdateRentalSecurityDeposit.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateRentalSecurityDeposit.ResponseValues response) {
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

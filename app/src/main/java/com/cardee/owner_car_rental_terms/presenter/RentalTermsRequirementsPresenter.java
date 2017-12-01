package com.cardee.owner_car_rental_terms.presenter;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.UpdateRentalRequirements;
import com.cardee.owner_car_rental_terms.RentalTermsContract;

public class RentalTermsRequirementsPresenter implements RentalTermsContract.Presenter {

    private final RentalTermsContract.View mView;

    private final UseCaseExecutor mExecutor;

    public RentalTermsRequirementsPresenter(RentalTermsContract.View view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void save(Object... objects) {
        int minAge = (int) objects[0];
        int maxAge = (int) objects[1];
        int experience = (int) objects[2];

        if (mView == null)
            return;

        mView.showProgress(true);
        int currentId = OwnerCarRepository.currentCarId();
        mExecutor.execute(new UpdateRentalRequirements(),
                new UpdateRentalRequirements.RequestValues(currentId, minAge, maxAge, experience),
                new UseCase.Callback<UpdateRentalRequirements.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateRentalRequirements.ResponseValues response) {
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

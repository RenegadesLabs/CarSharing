package com.cardee.owner_car_rental_info.terms.presenter;


import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.data_source.OwnerCarsRepository;
import com.cardee.data_source.remote.api.common.entity.CarRuleEntity;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.UpdateRentalRules;
import com.cardee.owner_car_rental_info.RentalContract;

public class RentalTermsRulesPresenter implements RentalContract.Presenter {

    private final RentalContract.View mView;

    private UseCaseExecutor mExecutor;

    public RentalTermsRulesPresenter(RentalContract.View view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void save(Object... objects) {
//        CarRuleEntity.Rule[] rules = (CarRuleEntity.Rule[]) objects[0];
        String otherRules = (String) objects[0];

        if (mView == null)
            return;

        mView.showProgress(true);
        mExecutor.execute(new UpdateRentalRules(),
                new UpdateRentalRules.RequestValues(OwnerCarRepository.currentCarId(), otherRules),
                new UseCase.Callback<UpdateRentalRules.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateRentalRules.ResponseValues response) {
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

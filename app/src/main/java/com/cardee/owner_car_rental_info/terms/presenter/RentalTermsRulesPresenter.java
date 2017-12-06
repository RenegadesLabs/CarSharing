package com.cardee.owner_car_rental_info.terms.presenter;


import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.data_source.remote.api.common.entity.CarRuleEntity;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.UpdateRentalRules;
import com.cardee.owner_car_rental_info.terms.RentalTermsContract;

public class RentalTermsRulesPresenter implements RentalTermsContract.Presenter {

    private final RentalTermsContract.View mView;

    private UseCaseExecutor mExecutor;

    public RentalTermsRulesPresenter(RentalTermsContract.View view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void save(Object... objects) {
        CarRuleEntity.Rule[] rules = (CarRuleEntity.Rule[]) objects[0];
        String otherRules = (String) objects[1];

        if (mView == null)
            return;

        mView.showProgress(true);
        mExecutor.execute(new UpdateRentalRules(),
                new UpdateRentalRules.RequestValues(OwnerCarRepository.currentCarId(), rules, otherRules),
                new UseCase.Callback<UpdateRentalRules.ResponseValues>() {
                    @Override
                    public void onSuccess(UpdateRentalRules.ResponseValues response) {
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

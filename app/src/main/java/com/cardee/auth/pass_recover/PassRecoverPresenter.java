package com.cardee.auth.pass_recover;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.user.usecase.PassRecover;
import com.cardee.mvp.BaseView;

public class PassRecoverPresenter {

    private final PassRecover mPassRecoverUseCase;
    private UseCaseExecutor mExecutor;
    private BaseView mView;

    public PassRecoverPresenter(BaseView view) {
        mPassRecoverUseCase = new PassRecover();
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    public void sendEmail(String email) {
        if (mView != null)
            mView.showProgress(true);

        mExecutor.execute(mPassRecoverUseCase, new PassRecover.RequestValues(email), new UseCase.Callback<PassRecover.ResponseValues>() {
            @Override
            public void onSuccess(PassRecover.ResponseValues response) {
                mView.showProgress(false);
                mView.showMessage("Reset password email sent!");
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
            }
        });
    }
}

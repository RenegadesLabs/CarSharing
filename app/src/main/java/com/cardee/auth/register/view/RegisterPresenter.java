package com.cardee.auth.register.view;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.CheckUniqueLogin;

public class RegisterPresenter {

    private RegisterView mView;

    private UseCaseExecutor mExecutor;

    public RegisterPresenter(RegisterView view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    public void checkUniqueLogin(String login, String password, String name) {
        if (mView != null)
            mView.showProgress(true);

        mExecutor.execute(new CheckUniqueLogin(), new CheckUniqueLogin.RequestValues(login, password, name),
                new UseCase.Callback<CheckUniqueLogin.ResponseValues>() {
            @Override
            public void onSuccess(CheckUniqueLogin.ResponseValues response) {
                mView.showProgress(false);
                mView.onValidationSuccess();
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
                mView.showMessage(error.getMessage());
            }
        });
    }
}

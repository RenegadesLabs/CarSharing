package com.cardee.auth.register.presenter;


import com.cardee.R;
import com.cardee.auth.register.view.RegisterView;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.CheckUniqueLogin;
import com.cardee.domain.owner.usecase.Register;

import java.io.File;

public class RegisterPresenter {

    private RegisterView mView;

    private UseCaseExecutor mExecutor;

    public RegisterPresenter(RegisterView view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    public void checkUniqueLogin(final String login, final String password) {
        if (mView != null)
            mView.showProgress(true);

        mExecutor.execute(new CheckUniqueLogin(), new CheckUniqueLogin.RequestValues(login, password),
                new UseCase.Callback<CheckUniqueLogin.ResponseValues>() {
                    @Override
                    public void onSuccess(CheckUniqueLogin.ResponseValues response) {
                        mView.showProgress(false);
                        mView.onValidationSuccess(login, password);
                    }

                    @Override
                    public void onError(Error error) {
                        mView.showProgress(false);
                        mView.showMessage(error.getMessage());
                    }
                });
    }

    public void signUp(String login, String password, String name, File picture) {
        mView.showProgress(true);
        mExecutor.execute(new Register(), new Register.RequestValues(login, password, picture, name),
                new UseCase.Callback<Register.ResponseValues>() {
                    @Override
                    public void onSuccess(Register.ResponseValues response) {
                        mView.showProgress(false);
                        mView.showMessage(R.string.signup_registration_success);
                        mView.onRegistrationSuccess();
                    }

                    @Override
                    public void onError(Error error) {
                        mView.showProgress(false);
                        mView.showMessage(error.getMessage());
                    }
                });
    }
}

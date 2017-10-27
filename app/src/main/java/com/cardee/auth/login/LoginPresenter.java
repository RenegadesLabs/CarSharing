package com.cardee.auth.login;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.UserRepository;
import com.cardee.data_source.remote.api.auth.request.SocialLoginRequest;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.Login;
import com.cardee.domain.owner.usecase.SocialLogin;

public class LoginPresenter {

    private final Login mLoginUseCase;
    private UseCaseExecutor mExecutor;
    private LoginView mView;

    public LoginPresenter(LoginView view) {
        mLoginUseCase = new Login();
        mExecutor = UseCaseExecutor.getInstance();
        mView = view;
    }

    public void login(String login, String password) {
        if (mView != null)
            mView.showProgress(true);

        mExecutor.execute(mLoginUseCase, new Login.RequestValues(login, password), new UseCase.Callback<Login.ResponseValues>() {
            @Override
            public void onSuccess(Login.ResponseValues response) {
                if (response.isSuccess()) {
                    mView.showProgress(false);
                    mView.onLoginSuccess();
                }
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
                if (error.getErrorType() == Error.Type.WRONG_CREDENTIALS) {
                    mView.showMessage(R.string.auth_wrong_cred);
                } else {
                    mView.showMessage(R.string.auth_error);
                }
            }
        });
    }

    public void loginSocial(SocialLoginRequest.Provider provider, String token) {
        if (mView != null)
            mView.showProgress(true);

        mExecutor.execute(new SocialLogin(), new SocialLogin.RequestValues(provider, token), new UseCase.Callback<SocialLogin.ResponseValues>() {
            @Override
            public void onSuccess(SocialLogin.ResponseValues response) {
                if (response.isSuccess()) {
                    mView.showProgress(false);
                    mView.onLoginSuccess();
                }
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
                mView.showMessage(R.string.auth_error);
            }
        });
    }
}

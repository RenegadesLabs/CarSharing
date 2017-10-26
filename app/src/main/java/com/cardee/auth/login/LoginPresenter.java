package com.cardee.auth.login;

import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.Login;

public class LoginPresenter {

    private final Login mLoginUseCase;
    private UseCaseExecutor mExecutor;

    public LoginPresenter() {
        mLoginUseCase = new Login();
        mExecutor = UseCaseExecutor.getInstance();
    }


}

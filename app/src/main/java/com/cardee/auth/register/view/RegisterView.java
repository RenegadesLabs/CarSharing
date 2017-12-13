package com.cardee.auth.register.view;


import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.mvp.BaseView;

import java.io.File;

public interface RegisterView extends BaseView {
    void onLogin();

    void onSignUp(String login, String password);

    void onFacebook();

    void onGoogle();

    void onProceedGoogleLogin(String accessToken);

    void onTermsOfService();

    void onTakePhoto();

    void onBackToFirstStep();

    void onSignUpAsOwner(String name, File picture);

    void onSignUpAsRenter(String name, File picture);

    void onValidationSuccess(String login, String password);

    void onRegistrationSuccess(AccountManager.ACC_STATE accState);
}

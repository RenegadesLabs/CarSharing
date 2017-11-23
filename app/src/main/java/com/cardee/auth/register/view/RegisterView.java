package com.cardee.auth.register.view;


import com.cardee.mvp.BaseView;

import java.io.File;

public interface RegisterView extends BaseView {
    void onLogin();

    void onSignUp(String login, String password);

//    void onFacebook();
//
//    void onGoogle();

    void onTermsOfService();

    void onTakePhoto();

    void onBackToFirstStep();

    void onSignUpAsOwner(String name, File picture);

    void onSignUpAsRenter(String name, File picture);

    void onValidationSuccess(String login, String password);

    void onRegistrationSuccess();
}
